package com.example.demo.model;

import java.util.Random;

public class ConfirmationUtil {
	public static String codeGenerate(int length) {
		// 使用可能な文字の定義
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        // 指定された長さまでランダムな文字列を生成
        for (int i = 0; i < length; i++) {
            // characters文字列からランダムに1文字選ぶ
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
	}
}
