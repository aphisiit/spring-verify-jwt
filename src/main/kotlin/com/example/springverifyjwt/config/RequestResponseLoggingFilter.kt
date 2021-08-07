package com.example.springverifyjwt.config

import com.example.springverifyjwt.utils.decodeJWTToken
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RequestResponseLoggingFilter: Filter {

    @Value("\${rsa-public-key}")
    lateinit var rsaPublicKey: String

    val LOG = LoggerFactory.getLogger(this.javaClass)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        val authorization: String? = req.getHeader("Authorization")
        if(authorization.isNullOrBlank()) {
            LOG.error("No token found")
            throw SecurityException("No token found")
        } else {
            decodeJWTToken(authorization.replace("Bearer ", ""), rsaPublicKey)
        }

        chain!!.doFilter(request, response)
    }


}