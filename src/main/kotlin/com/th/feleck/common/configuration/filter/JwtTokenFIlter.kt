
import com.th.feleck.common.util.JwtTokenUtils
import com.th.feleck.user.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
    @Value("\${jwt.secret-key}")
    private val key: String,
    private val userService: UserService

) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //get header

        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || !header.startsWith("Bearer ")){
            logger.error("error occurs while getting header. header is null or invalid")
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = header.split(" ")[1].trim()

            //TODO : check token is valid
            if(JwtTokenUtils.isExpired(token, key)){
                logger.error("key is expired")
                return
            }
            //TODO : get username from token
            val userName = JwtTokenUtils.getUserName(token, key)
            val user = userService.loadUserByUsername(userName)

            //TODO : check the user is valid

            val authentication = UsernamePasswordAuthenticationToken(
                //TODO
                user, null, mutableListOf(SimpleGrantedAuthority(user.role.toString()))
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication

        } catch (e: Exception) {
            logger.error("error occurs while validating. ${e}")
            filterChain.doFilter(request, response)
            return
        }
    }

}
