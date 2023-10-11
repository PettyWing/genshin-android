package com.youer.genshin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author youer
 * @date 9/27/23
 */
public class StorageUtil {

    public static boolean fileExists(Context context, String fileName) {
        File file = new File(context.getFilesDir() + File.separator + fileName);
        return file.exists();
    }

    /**
     * 将JSON内容写入文件并保存到内部存储
     *
     * @param context     上下文
     * @param jsonContent JSON内容
     * @param fileName    文件名
     * @return true-保存成功，false-保存失败
     */
    public static boolean saveJsonToFile(Context context, String jsonContent, String fileName) {
        FileOutputStream fileOutputStream = null;

        try {
            File file = new File(context.getFilesDir() + File.separator + fileName);
            fileOutputStream = new FileOutputStream(file.getPath());
            fileOutputStream.write(jsonContent.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 从内部存储读取文件内容
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 文件内容，如果读取失败则返回null
     */
    public static String readJsonFromFile(Context context, String fileName) {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
            File file = new File(context.getFilesDir() + File.separator + fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JsonObject readJsonFromAssets(String fileName) {
        try {
            InputStream inputStream = StorageUtil.class.getClassLoader().getResourceAsStream("assets/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();

            String json = stringBuilder.toString();
            return new Gson().fromJson(json, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}