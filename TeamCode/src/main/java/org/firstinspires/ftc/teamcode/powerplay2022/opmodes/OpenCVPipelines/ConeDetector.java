package org.firstinspires.ftc.teamcode.powerplay2022.opmodes.OpenCVPipelines;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.OpenCvCamera;

public class ConeDetector extends OpenCvPipeline {
    OpenCvWebcam coneCam1 = null;
    public void startCamera(HardwareMap hardwareMap){
        WebcamName coneCamName = hardwareMap.get(WebcamName.class, "webcam1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id", hardwareMap.appContext.getPackageName());
        coneCam1 = OpenCvCameraFactory.getInstance().createWebcam(coneCamName, cameraMonitorViewId);
        //coneCam1.setPipeline(new examplePipeline());
        coneCam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            public void onOpened() {
                coneCam1.startStreaming(640,480);
            }
            @Override
            public void onError(int errorCode) {
            }
        });
    }
    @Override
    public Mat processFrame(Mat input) {
        return null;
    }
}