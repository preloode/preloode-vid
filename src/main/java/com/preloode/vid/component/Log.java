package com.preloode.vid.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


@Component
public class Log {


    @Autowired
    private DateTime dateTime;


    public Boolean exception(HttpServletRequest request, Exception exception) {

        Boolean result = false;

        ServletContext context = request.getSession().getServletContext();
        File file = new File(context.getRealPath("/WEB-INF") + "/log/error.txt");

        try {

            if (!file.exists()) {

                file.createNewFile();

            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(this.dateTime.getTimestamp(request).toString());
            bufferedWriter.newLine();
            bufferedWriter.write(exception.getMessage());
            bufferedWriter.newLine();

            StackTraceElement[] getStackTrace = exception.getStackTrace();

            for (StackTraceElement stackTraceElement : getStackTrace) {

                bufferedWriter.write(stackTraceElement.toString());
                bufferedWriter.newLine();

            }

            bufferedWriter.newLine();
            bufferedWriter.close();

            result = true;

        } catch (Exception logException) {

            logException.printStackTrace();

        }

        return result;

    }


}
