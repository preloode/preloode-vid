package com.preloode.vid.configuration;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class Upload {


    private ArrayList<String> audio;

    private ArrayList<String> image;

    private ArrayList<String> video;


    public ArrayList<String> getAudio() {

        this.setAudio(new ArrayList<String>() {

            {
                add("mp3");
                add("aac");
            }

        });

        return this.audio;

    }


    public void setAudio(ArrayList<String> audio) {

        this.audio = audio;

    }


    public ArrayList<String> getImage() {

        this.setImage(new ArrayList<String>() {

            {
                add("gif");
                add("jpg");
                add("jpeg");
                add("png");
            }

        });

        return this.image;

    }


    public void setImage(ArrayList<String> image) {

        this.image = image;

    }


    public ArrayList<String> getVideo() {

        this.setVideo(new ArrayList<String>() {

            {
                add("avi");
                add("mkv");
                add("mp4");
            }

        });

        return this.video;

    }


    public void setVideo(ArrayList<String> video) {

        this.video = video;

    }


}
