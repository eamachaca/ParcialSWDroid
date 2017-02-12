package com.hamonteroa.ocrtest_m2;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.hamonteroa.ocrtest_m2.camera.GraphicOverlay;

import java.util.ArrayList;

/**
 * Created by hamonteroa on 1/27/17.
 */

public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private ArrayList<String> eaDeIt0;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay,ArrayList<String>deito) {
        mGraphicOverlay = ocrGraphicOverlay;
        this.eaDeIt0=deito;
    }

    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        eaDeIt0.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                Log.d("Processor", "Text detected! " + item.getValue());
                eaDeIt0.add(item.getValue());
            }
            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
        }
    }
    private ArrayList<String> getEaDeIt0(){
        return eaDeIt0;
    }
}
