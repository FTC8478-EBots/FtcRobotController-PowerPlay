package org.firstinspires.ftc.teamcode.powerplay2022.opmodes.OpenCVPipelines;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.ebotscv.AprilTagDetectionPipeline;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvWebcam;
import org.openftc.easyopencv.OpenCvCamera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import java.util.ArrayList;
public class ConeDetector extends OpenCvPipeline {
    OpenCvWebcam coneCam1 = null;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    public void startCamera(HardwareMap hardwareMap,String webcamName){
        WebcamName coneCamName = hardwareMap.get(WebcamName.class, webcamName);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id", hardwareMap.appContext.getPackageName());
        coneCam1 = OpenCvCameraFactory.getInstance().createWebcam(coneCamName, cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(0.03,822.317,822.317,319.495,242.502);
        coneCam1.setPipeline(aprilTagDetectionPipeline);
        coneCam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            public void onOpened() {
                coneCam1.startStreaming(640,480);
            }
            @Override
            public void onError(int errorCode) {
            }
        });
    }
    public int getParkingSpace(){
        int parkingSpace = -1;
       ArrayList <AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();
        if (detections == null )
        {return parkingSpace;}
        for (AprilTagDetection aprilTagDetection : detections) {


         return aprilTagDetection.id;
        }
        return parkingSpace;
    }
    @Override
    public Mat processFrame(Mat input) {
        return null;
    }
}