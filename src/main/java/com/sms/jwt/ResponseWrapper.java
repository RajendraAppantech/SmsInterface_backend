package com.sms.jwt;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseWrapper extends HttpServletResponseWrapper {
   private HttpServletResponse response;
   private ByteArrayOutputStream bos;

   public ResponseWrapper(HttpServletResponse response) {
      super(response);
      this.response = response;
      this.bos = new ByteArrayOutputStream();
   }

   public ServletOutputStream getOutputStream() throws IOException {
      return new ServletOutputStream() {
         public void write(int b) throws IOException {
            ResponseWrapper.this.bos.write(b);
         }

         public boolean isReady() {
            return true;
         }

         public void setWriteListener(WriteListener listener) {
         }
      };
   }

   public byte[] getResponseBody() {
      return this.bos.toByteArray();
   }
}
