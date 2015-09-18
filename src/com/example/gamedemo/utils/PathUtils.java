package com.example.gamedemo.utils;

import android.util.Log;

//统一管理项目中的网址
public class PathUtils {

	private final static String TAG = "url";

	// http://www.3dmgame.com/sitemap/api.php?row=<记录数>&typeid=<分类ID>&paging=1&page=n
	// http://122.226.122.6/sitemap/api.php?row=20&typeid=1&paging=1&page=1
	public PathUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String getArticlePath(int row, int typeid, int pagenum) {
		// Log.d(TAG, "-->>" + "http://122.226.122.6/sitemap/api.php?row=" + row
		// + "&typeid=" + typeid + "&paging=1&page="
		// + pagenum);
		return "http://122.226.122.6/sitemap/api.php?row=" + row + "&typeid="

		+ typeid + "&paging=1&page=" + pagenum;
	}

	public static String getPic() {
		return "http://122.226.122.6";
	}

	public static String getDetailPath(String id, String typeid) {
		// Log.d(TAG, "-->>" + "http://122.226.122.6/sitemap/api.php?id=" + id +
		// "&typeid=" + typeid);
		return "http://122.226.122.6/sitemap/api.php?id=" + id + "&typeid=" + typeid;
	}

	public static String getComment(String id, String pageNum) {
		Log.d(TAG, "-->Comment" + "http://122.226.122.6/sitemap/api.php?type=1&aid=" + id + "&pageno=" + pageNum);
		return "http://122.226.122.6/sitemap/api.php?type=1&aid=" + id + "&pageno=" + pageNum;
	}

	public static String getGamePath(int row, int typeid, int pagenum) {
		Log.d(TAG, "-->>" + "http://122.226.122.6/sitemap/api.php?row=" + row + "&typeid=" + typeid + "&paging=1&page="
				+ pagenum);
		return "http://122.226.122.6/sitemap/api.php?row=" + row + "&typeid="

		+ typeid + "&paging=1&page=" + pagenum;
	}

}
