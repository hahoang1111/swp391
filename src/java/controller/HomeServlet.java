/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import entity.Categories;
import entity.Subjects;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import service.CategoryDAO;
import service.CourseDAO;
import ulti.Paging;

/**
 *
 * @author The Shuyy
 */
public class HomeServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String pageNum = request.getParameter("page");
            int page = 1;
            if(pageNum !=  null){
                page = Integer.parseInt(pageNum);
            }
            int pageSize = 5;
            String categoryID = request.getParameter("categoryID");
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Categories> categories = categoryDAO.getAll();
            CourseDAO courseDAO = new CourseDAO();
            List<Subjects> subjects = courseDAO.getSubjects(categoryID != null ? Integer.parseInt(categoryID) : null);
            int total = subjects.size() % pageSize == 0? (subjects.size() / pageSize) : (subjects.size() / pageSize + 1);
            List paging = Paging.Pagination(page, pageSize, subjects);
            request.setAttribute("categories", categories);
            request.setAttribute("subjects", paging);
            request.setAttribute("total", total);
            request.setAttribute("page", page);
            request.getRequestDispatcher("courses.jsp").forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
