package com.epam.burmensky.hospital.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PaginationTagHandler extends TagSupport {

    private static final int PAGES_IN_BLOCK = 5;

    private int currentPage;
    private int pageNumber;
    private byte sortingMode;
    private String url;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSortingMode(byte sortingMode) { this.sortingMode = sortingMode; }

    public void setUrl(String url) {
        this.url = url;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int startPage = (currentPage - 2 >= 0) ? currentPage - 2 : 0;
        int endPage = Math.min(startPage + PAGES_IN_BLOCK, pageNumber);
        String sortingModeString = (sortingMode == 0) ? "" : "&sortingMode=" + sortingMode;

        try {
            out.write("<nav>");
            out.write("<ul class='pagination'>");
            out.write((currentPage == 0) ? "<li class='page-item disabled'>" : "<li class='page-item'>");
            out.write((currentPage == 0) ? "<span class='page-link'>&laquo;</span>" :
                    "<a class='page-link' href='" + url + "&page=" + (currentPage - 1) +
                            sortingModeString + "'>&laquo;</a>");
            out.write("</li>");
            for (int p = startPage; p < endPage; p++) {
                if (p == currentPage) {
                    out.write("<li class='page-item active' aria-current='page'>" +
                        "<span class='page-link'>" + (p + 1) + "</span></li>");
                }
                else {
                    out.write("<li class='page-item'>" +
                        "<a class='page-link' href='" + url + "&page=" + p +
                            sortingModeString + "'>" + (p + 1) + "</a></li>");
                }
            }

            out.write((currentPage == pageNumber - 1) ? "<li class='page-item disabled'>" : "<li class='page-item'>");
            out.write((currentPage == pageNumber - 1) ? "<span class='page-link'>&raquo;</span>" :
                    "<a class='page-link' href='" + url + "&page=" + (currentPage + 1) +
                            sortingModeString + "'>&raquo;</a>");
            out.write("</li>");
            out.write("</ul>");
            out.write("</nav>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
