package com.devsu.finance.utils;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    private static final String CLASSPATH = "classpath:";

    public static <T> T convertJSONFileToObject(String filePath, Class<T> valueType) throws IOException {
      return new Gson().fromJson(FileUtils
              .readFileToString(ResourceUtils.getFile(CLASSPATH.concat(filePath)), StandardCharsets.UTF_8), valueType);
    }
}
