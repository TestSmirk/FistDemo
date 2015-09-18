package com.example.gamedemo.utils;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * 异步加载图片
 * 
 * @author jqw
 * 
 */
@SuppressLint("NewApi")
public class AsyncImageLoader {
	private static final int LOAD_SUCCESS = 1;
	/** 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存 */
	private static LruCache<String, Bitmap> lruCache;
	/** 文件操作工具类 */
	private FileUtils utils;
	private static ThreadPoolExecutor executor;
	private static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(
			4);
	private static final int CORE_POOL_SIZE = 1;
	private static final int MAXIMUM_POOL_SIZE = 4;
	private static final int KEEP_ALIVE = 10;
	static {
		// 开启线程池 最小线程数
		executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
				KEEP_ALIVE, TimeUnit.SECONDS, queue,
				new ThreadPoolExecutor.DiscardOldestPolicy());
		// 获取系统分配给应用程序的最大内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int maxSize = maxMemory / 8;
		lruCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// 测量Bitmap的大小 默认返回图片数量
				return value.getRowBytes() * value.getHeight();
			}
		};
	}

	public AsyncImageLoader(Context context) {
		super();
		utils = new FileUtils(context);
	}

	/**
	 * 
	 * @Title: downLoader
	 * @说 明: 加载图片
	 * @参 数: @param url
	 * @参 数: @param loaderlistener
	 * @参 数: @return
	 * @return Bitmap 返回类型
	 * @throws
	 */
	@SuppressLint("HandlerLeak")
	public Bitmap downLoader(final ImageView imageView,
			final ImageLoaderlistener loaderlistener) {
		final String url = (String) imageView.getTag();
		if (url != null) {
			final Bitmap bitmap = showCacheBitmap(url);
			if (bitmap != null) {
				return bitmap;
			} else {
				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						loaderlistener.onImageLoader((Bitmap) msg.obj,
								imageView);
					}
				};

				executor.execute(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = HttpUtils.getBitmapFormUrl(url);
						if (bitmap != null) {
							Message msg = handler.obtainMessage();
							msg.obj = bitmap;
							msg.what = LOAD_SUCCESS;
							handler.sendMessage(msg);
							try {
								utils.savaBitmap(url, bitmap);
							} catch (IOException e) {
								e.printStackTrace();
							}
							lruCache.put(url, bitmap);
						}
					}
				});
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: showCacheBitmap
	 * @说 明: 获取bitmap对象 : 内存中没有就去sd卡中去找
	 * @参 数: @param url 图片地址
	 * @参 数: @return
	 * @return Bitmap 返回类型 图片
	 * @throws
	 */
	public Bitmap showCacheBitmap(String url) {
		Bitmap bitmap = getMemoryBitmap(url);
		if (bitmap != null) {
			return bitmap;
		} else if (utils.isFileExists(url) && utils.getFileSize(url) > 0) {
			bitmap = utils.getBitmap(url);
			if (url != null && bitmap != null) {
				lruCache.put(url, bitmap);
			}

			return bitmap;
		}
		return null;
	}

	/**
	 * 
	 * @Title: getMemoryBitmap
	 * @说 明:获取内存中的图片
	 * @参 数: @param url
	 * @参 数: @return
	 * @return Bitmap 返回类型
	 * @throws
	 */
	private Bitmap getMemoryBitmap(String url) {
		return lruCache.get(url);
	}

	public interface ImageLoaderlistener {
		public void onImageLoader(Bitmap bitmap, ImageView imageView);
	}

	/**
	 * 
	 * @Title: cancelTask
	 * @说 明:停止所有下载线程
	 * @参 数:
	 * @return void 返回类型
	 * @throws
	 */
	public void cancelTask() {
		if (executor != null) {
			executor.shutdownNow();
		}
	}

}
