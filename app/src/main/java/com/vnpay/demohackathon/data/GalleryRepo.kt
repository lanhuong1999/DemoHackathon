package com.vnpay.demohackathon.data

import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.vnpay.demohackathon.data.model.ImageEnitity

object GalleryRepo {

    fun getListImages(context:Context, limit:Int, page:Int):MutableList<ImageEnitity>{
        val list = mutableListOf<ImageEnitity>()

        val resolver = context.contentResolver
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projections = arrayOf(
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.IS_PRIVATE
        )

        val orderBy = MediaStore.Images.Media.DATE_MODIFIED
        val cursorImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver.query(externalUri, projections, Bundle().apply {
                putString(ContentResolver.QUERY_ARG_SQL_SELECTION, null)
                putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, null)
                putStringArray(
                    ContentResolver.QUERY_ARG_SORT_COLUMNS,
                    arrayOf(MediaStore.Images.Media.DATE_MODIFIED)
                )
                putInt(
                    ContentResolver.QUERY_ARG_SORT_DIRECTION,
                    ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                )
                putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
                putInt(ContentResolver.QUERY_ARG_OFFSET, limit * page)

            }, null)
        } else {
            resolver.query(
                externalUri,
                projections,
                null,
                null,
                "$orderBy DESC LIMIT $limit OFFSET ${page * limit}"
            )
        }

        cursorImage?.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val path =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                    list.add(ImageEnitity().apply {
                        this.id = id
                        this.path = path
                    })
                } while (cursor.moveToNext())
            }
        }

        return list

    }
}