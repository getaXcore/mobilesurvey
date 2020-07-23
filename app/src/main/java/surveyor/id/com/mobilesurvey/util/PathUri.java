package surveyor.id.com.mobilesurvey.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Created by 001810240 on 4/2/2020.
 */

public class PathUri {
    private final Context context;

    public PathUri(Context context){
        this.context = context;
    }

    public String getPathFromURI(final Uri uri){
        final boolean isKitkat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        //DocumentProvider
        if (isKitkat && DocumentsContract.isDocumentUri(context,uri)){
            final String id = DocumentsContract.getDocumentId(uri);
            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));

            //ExternalStorageProvider
            if (isExternalStorageDocument(uri)){
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory()+"/"+split[1];
                }
            }
            //DownloadsProvider
            else if (isDownloadDocument(uri)){
                return getDataColumn(contentUri,null,null);
            }
            //MediaProvider
            else if (isMediaDocument(uri)){
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("image".equals(type)){
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }else if ("video".equals(type)){
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }else if ("audio".equals(type)){
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[]selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(contentUri,selection,selectionArgs);
            }
        }
        //MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())){
            return getDataColumn(uri,null,null);
        }
        //File
        else if ("file".equalsIgnoreCase(uri.getScheme())){
            return uri.getPath();
        }

        return null;

    }

    public String getDataColumn(Uri uri,String selection,String[]selectionArgs){
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri,projection,selection,selectionArgs,null);
            if (cursor != null && cursor.moveToFirst()){
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri){
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public static boolean isExternalStorageDocument(Uri uri){
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadDocument(Uri uri){
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
