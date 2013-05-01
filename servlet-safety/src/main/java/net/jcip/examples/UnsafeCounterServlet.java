package net.jcip.examples;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * UnsafeCounterServlet
 *
 * Servlet that counts requests without the necessary synchronization
 *
 * @author Brian Goetz and Tim Peierls
 */
@NotThreadSafe
public class UnsafeCounterServlet extends HttpServlet implements Servlet {
    private long count = 0;

    public long getCount() {
        return count;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().log(req.getServletPath());
        if (req.getServletPath().equals("/reset")) {
            count = 0;
            return;
        }
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(resp, count);
    }

    private void encodeIntoResponse(ServletResponse resp, long count) throws IOException {
        resp.getWriter().format("count=%d\n", count);
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
