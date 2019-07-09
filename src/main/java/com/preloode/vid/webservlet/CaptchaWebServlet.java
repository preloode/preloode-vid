package com.preloode.vid.webservlet;

import com.preloode.vid.component.RsaEncryption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@WebServlet("/captcha")
public class CaptchaWebServlet extends HttpServlet {


    @Autowired
    private RsaEncryption rsaEncryption;

    private String file;

    private Integer height;

    private Integer length;

    private Integer width;


    public String getFile() {

        return this.file;

    }


    public void setFile(String file) {

        this.file = file;

    }


    public Integer getHeight() {

        return this.height;

    }


    public void setHeight(Integer height) {

        this.height = height;

    }


    public Integer getLength() {

        return this.length;

    }


    public void setLength(Integer length) {

        this.length = length;

    }


    public Integer getWidth() {

        return this.width;

    }


    public void setWidth(Integer width) {

        this.width = width;

    }


    @PostConstruct
    private void initialize() {

        Map<String, Object> setting = new HashMap<String, Object>() {

            {
                put("file", "png");
                put("height", 40);
                put("length", 5);
                put("width", 120);
            }

        };

        this.setFile(setting.get("file").toString());

        this.setHeight(Integer.parseInt(setting.get("height").toString()));

        this.setLength(Integer.parseInt(setting.get("length").toString()));

        this.setWidth(Integer.parseInt(setting.get("width").toString()));

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Progma", "no-cache");
        response.setDateHeader("Max-Age", 0);

        String text = this.initializeText(this.getLength());

        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics2D.setFont(new Font("Serif", Font.PLAIN, 26));
        graphics2D.setColor(Color.blue);

        Integer start = 10;
        byte[] textByte = text.getBytes();
        Random random = new Random();

        for (Integer i = 0; i < textByte.length; i++) {

            graphics2D.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            graphics2D.drawString(new String(new byte[]{textByte[i]}), start + (i * 20), (int) (Math.random() * 20 + 20));

        }

        graphics2D.setColor(Color.white);

        for (Integer i = 0; i < 8; i++) {

            graphics2D.drawOval((int) (Math.random() * 160), (int) (Math.random() * 10), 30, 30);

        }

        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, this.getFile(), outputStream);
        outputStream.close();

        session.setAttribute("captcha", text);

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);

    }


    private String initializeText(Integer length) {

        String result = "";
        String character = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();

        while (stringBuffer.length() < length) {

            Integer index = (int) (random.nextFloat() * character.length());
            stringBuffer.append(character.substring(index, index + 1));

        }

        result = stringBuffer.toString();

        return result;

    }


}
