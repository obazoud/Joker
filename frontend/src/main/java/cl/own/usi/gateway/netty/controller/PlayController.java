package cl.own.usi.gateway.netty.controller;

import static cl.own.usi.gateway.netty.ResponseHelper.writeResponse;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.NOT_IMPLEMENTED;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.UNAUTHORIZED;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import cl.own.usi.model.Question;

/**
 * Controller that send asynchronously the {@link Question}
 *
 * @author nicolas
 */
@Component
public class PlayController extends AbstractController {

	public static final String URI_PLAY = "/play/";


	@Value(value = "classpath:template/play.html")
	private Resource playTemplate;



	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		HttpRequest request = (HttpRequest) e.getMessage();

		String uri = request.getUri();
		uri = uri.substring(URI_API_LENGTH);

		if (request.getMethod() == HttpMethod.GET) {
			String userId = getCookie(request, COOKIE_AUTH_NAME);

			if (userId == null) {
				writeResponse(e, UNAUTHORIZED);
				getLogger().info("User not authorized");
			} else {
				writeHtml(e, playTemplate);
			}
		} else {
			writeResponse(e, NOT_IMPLEMENTED);
			getLogger().info("Wrong method");
		}

	}

}