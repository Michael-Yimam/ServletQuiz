package com.quizservlet.servlets;

import com.quizservlet.java.Grade;
import com.quizservlet.java.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(name = "QuizServlet", urlPatterns = {"/getQuiz"})
public class QuizServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession s = request.getSession(false);
        if(s == null)
        {
            doGet(request, response);
        }else{
            s = request.getSession();

            int guess = 0;
            int qNumber = (Integer)s.getAttribute("qNumber");

            if(qNumber > Quiz.questions.length){
                doGet(request, response);
                return;
            }

            Grade g = (Grade) s.getAttribute("grade");

            try{

                guess = Integer.parseInt(request.getParameter("guess"));

            }catch(NumberFormatException nfe){
                System.out.println(nfe);
            }


            if(Quiz.gradeQuiz(qNumber, guess)){
                g.grade[qNumber-1] = 1;
                s.setAttribute("grade", g);
            }

            qNumber++;
            s.setAttribute("qNumber", qNumber);
            int sum = Arrays.stream(g.grade)
                    .reduce(0, Integer::sum);

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>The Number Quiz</h1>");
            out.println("<p>Your current score is " + sum + "</p>");

            if(qNumber > 5){
                out.println("<p>You have completed the Number Quiz, with a score of " + sum + " out of 5.</p>");
            }else{

                out.println("<p>Guess the next number in the sequences.</p>");
                out.println("<p>" + Quiz.questions[qNumber-1] + "</p>");
                out.println("<form action='getQuiz' method='post'>");
                out.println("<p> Your answer: <input name='guess' type='text' size='5'></p>");
                out.println("<input type='submit' value='submit'></form>");

            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession s = request.getSession();
        s.setAttribute("quiz", new Quiz());
        s.setAttribute("qNumber", 1);
        s.setAttribute("answer", null);
        s.setAttribute("grade", new Grade());

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>The Number Quiz</h1>");
        out.println("<p>Your current score is </p>");
        out.println("<p>Guess the next number in the sequences.</p>");
        out.println("<p>" + Quiz.questions[0] + "</p>");
        out.println("<form action='getQuiz' method='post'>");
        out.println("<p> Your answer: <input name = 'guess' type='text' size='5'></p>");
        out.println("<input type='submit' value='submit'></form>");
    }

}
