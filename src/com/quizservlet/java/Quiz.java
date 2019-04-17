package com.quizservlet.java;

import javax.servlet.http.HttpSession;

public class Quiz {

    public final static String[] questions = {
            "3, 1, 4, 1, 5", // pi
            "1, 1, 2, 3, 5", // fibonacci
            "1, 4, 9, 16, 25", // squares
            "2, 3, 5, 7, 11", // primes
            "1, 2, 4, 8, 16" // powers of 2
    };

    public final static int[] answers = {9, 8, 36, 13, 32};

    public static boolean gradeQuiz(int q, int a){

        return answers[q-1] == a;
    }

}
