package com.onix.scalecamerapreview.interfaces;

import com.onix.scalecamerapreview.models.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
}
