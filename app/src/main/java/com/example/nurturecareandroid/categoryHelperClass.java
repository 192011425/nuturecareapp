package com.example.nurturecareandroid;

public class categoryHelperClass {

    String ServiceName, Information , ImageView;

    public categoryHelperClass(String serviceName, String information, String imageView) {
        ServiceName = serviceName;
        Information = information;
        ImageView = imageView;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public String getInformation() {
        return Information;
    }

    public String getImageView() {
        return ImageView;
    }
}
