https://github.com/codepath/android_guides/wiki/Sharing-Content-with-Intents

XML tag	                ---------   Corresponding storage call	    ---------   When to use

<files-path>	        ---------   Context.getFilesDir()	        ---------   data can only be viewed by app, deleted when uninstalled (/data/data/[packagename]/files)

<external-files-dir>	---------   Context.getExternalFilesDir()   ---------   data can be read/write by the app, any apps granted with READ_STORAGE permission can read too, deleted when uninstalled (/Android/data/[packagename]/files)

<cache-path>	        ---------   Context.getCacheDir()	        ---------   temporary file storage

<external-path>	        ---------   Environment.getExternalStoragePublicDirectory()	---------   data can be read/write by the app, any apps can view, files not deleted when uninstalled

<external-cache-path>	---------   Context.getExternalCacheDir()	   ---------   temporary file storage with usually larger space


https://proandroiddev.com/sharing-files-though-intents-are-you-ready-for-nougat-70f7e9294a0b

