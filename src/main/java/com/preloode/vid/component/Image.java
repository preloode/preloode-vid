package com.preloode.vid.component;

import com.preloode.vid.configuration.Upload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Component
public class Image {


    @Autowired
    private Upload upload;

    @Autowired
    private Log log;


    public Map<String, Object> cropSquare(HttpServletRequest request, String path, String fileName) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
                put("response", "File failed cropped");
            }

        };

        ServletContext context = request.getSession().getServletContext();

        try {

            Integer x = 0;
            Integer y = 0;

            BufferedImage originalImage = ImageIO.read(new File(context.getRealPath("/WEB-INF") + path + "/" + fileName));
            String extension = FilenameUtils.getExtension(context.getRealPath("/WEB-INF") + path + "/" + fileName);
            Integer width = originalImage.getWidth();
            Integer height = originalImage.getHeight();

            if (width != height) {

                if (width > height) {

                    x = (width - height) / 2;
                    width = height;

                } else if (height > width) {

                    y = (height - width) / 2;
                    height = width;

                }

            }

            BufferedImage bufferedImage = originalImage.getSubimage(x, y, width, height);
            ImageIO.write(bufferedImage, extension, new File(context.getRealPath("/WEB-INF") + path + "/" + fileName));

            result.put("result", true);

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


    public boolean delete(HttpServletRequest request, String path, String fileName) {

        Boolean result = false;

        ServletContext context = request.getSession().getServletContext();

        try {

            File file = new File(context.getRealPath("/WEB-INF") + path + "/" + fileName);
            result = Files.deleteIfExists(file.toPath());

            result = true;

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


    public Map<String, Object> resize(HttpServletRequest request, String path, String fileName, Integer maxWidth, Integer maxHeight) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
                put("response", "File failed resized");
            }

        };

        ServletContext context = request.getSession().getServletContext();

        try {

            BufferedImage originalImage = ImageIO.read(new File(context.getRealPath("/WEB-INF") + path + "/" + fileName));
            String extension = FilenameUtils.getExtension(context.getRealPath("/WEB-INF") + path + "/" + fileName);
            double originalWidth = originalImage.getWidth();
            double originalHeight = originalImage.getHeight();
            double width = originalWidth;
            double height = originalHeight;

            if (originalWidth > maxWidth) {

                width = maxWidth;
                height = (maxWidth / originalWidth) * originalHeight;

            } else if (originalHeight > maxHeight) {

                width = (height / originalHeight) * originalWidth;
                height = maxHeight;

            }

            Integer resizedWidth = (int) width;
            Integer resizeHeight = (int) height;
            BufferedImage bufferedImage = new BufferedImage(resizedWidth, resizeHeight, BufferedImage.TYPE_INT_RGB);

            if (extension.equals("png")) {

                bufferedImage = new BufferedImage(resizedWidth, resizeHeight, BufferedImage.TYPE_INT_ARGB);

            }

            Graphics2D graphic = bufferedImage.createGraphics();
            graphic.drawImage(originalImage, 0, 0, resizedWidth, resizeHeight, new Color(255, 255, 255, 0), null);
            graphic.dispose();

            ImageIO.write(bufferedImage, extension, new File(context.getRealPath("/WEB-INF") + path + "/" + fileName));

            result.put("result", true);

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


    public Map<String, Object> upload(HttpServletRequest request, MultipartHttpServletRequest multipartRequest, String path, Integer maxSize) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("result", false);
                put("response", "File failed uploaded");
            }

        };

        ArrayList<String> allowedType = (ArrayList<String>) this.upload.getImage();

        Iterator<String> files = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;
        ServletContext context = request.getSession().getServletContext();

        while (files.hasNext()) {

            Map<String, Object> validation = new HashMap<String, Object>() {

                {
                    put("extension", false);
                    put("size", false);
                }

            };

            multipartFile = multipartRequest.getFile(files.next());
            String fileName = multipartFile.getOriginalFilename().replaceAll("[^A-Za-z0-9\\s.-]+", "").replace(" ", "-").toLowerCase();
            String extension = FilenameUtils.getExtension(fileName);

            for (String string : allowedType) {

                if (extension.equals(string)) {

                    validation.put("extension", true);

                    break;

                } else {

                    String message = "Only ";

                    for (String stringChild : allowedType) {

                        message += " ," + stringChild;

                    }

                    message += " file allowed";
                    message = message.replaceFirst(" ,", "");

                    result.put("response", message);

                }

            }

            Integer fileSize = (int) Math.ceil(multipartFile.getSize() / 1024);

            if (fileSize <= maxSize) {

                validation.put("size", true);

            } else {

                result.put("response", "Maximum " + Math.ceil(maxSize / 1024) + " Mb file allowed");

            }

            Boolean valid = true;

            for (Map.Entry<String, Object> map : validation.entrySet()) {

                if (map.getValue().equals(false)) {

                    valid = false;

                    break;

                }

            }

            if (valid == true) {

                File checkDir = new File(context.getRealPath("/WEB-INF") + path + "/" + fileName);
                String fileNameResult = fileName;
                ArrayList<String> fileResult = new ArrayList<String>();
                Integer i = 1;

                while (checkDir.exists()) {

                    fileNameResult = FilenameUtils.removeExtension(fileName) + "_" + i + "." + extension;
                    checkDir = new File(context.getRealPath("/WEB-INF") + path + "/" + fileNameResult);

                    i++;

                }

                try {

                    FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(context.getRealPath("/WEB-INF") + path + "/" + fileNameResult));
                    fileResult.add(fileNameResult);

                    result.put("result", true);

                } catch (Exception exception) {

                    this.log.exception(request, exception);

                }

                result.put("result", true);
                result.put("response", "File successfully uploaded");
                result.put("file", fileResult);

            }

        }

        return result;

    }


}
