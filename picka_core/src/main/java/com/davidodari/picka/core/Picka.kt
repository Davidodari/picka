package com.davidodari.picka.core

import android.app.Activity
import android.content.Intent
import com.davidodari.picka.core.exceptions.MimeTypeException

/**
 * Created By David Odari
 * On 14/08/19
 *
 **/
class Picka {

    //TODO Support multiple media types
    companion object {

        private const val ACTION_PICK_MEDIA = 1

        /**
         * @param activity caller activity from which image is selected
         * @param mediaType typeFormat of media to be presented
         * @param mimetypes the supported mimetypes for specified mediatype
         */
        @JvmOverloads
        fun pickMedia(
            activity: Activity,
            mediaType: MediaType = MediaType.IMAGE,
            mimetypes: Array<String>? = null
        ) {

            val intent = Intent().apply {
                val typeFormat = mediaType.typeFormat
                val typeName = typeFormat.substring(0, typeFormat.indexOf('/'))

                type = typeFormat
                action = Intent.ACTION_GET_CONTENT

                mimetypes?.let { types ->
                    types.forEach { type ->
                        if (!type.contains(typeName)) {
                            throw MimeTypeException("Invalid Mime Type Format, use $typeName mime types")
                        }
                    }
                    putExtra(Intent.EXTRA_MIME_TYPES, types)
                }
            }

            val media: String = when (mediaType) {
                MediaType.IMAGE -> activity.getString(R.string.label_media_type_image)
                MediaType.VIDEO -> activity.getString(R.string.label_media_type_video)
            }
            activity.startActivityForResult(
                Intent.createChooser(
                    intent,
                    activity.getString(R.string.label_select_media, media)
                ), ACTION_PICK_MEDIA
            )
        }
    }
}