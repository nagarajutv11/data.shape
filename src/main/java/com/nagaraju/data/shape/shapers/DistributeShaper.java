package com.nagaraju.data.shape.shapers;

import com.google.gson.JsonObject;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.handlers.DirectoryHandler;
import com.nagaraju.data.shape.handlers.RequestHandler;
import com.nagaraju.data.shape.handlers.Webhook;
import com.nagaraju.data.shape.handlers.WebhookHandler;

public class DistributeShaper extends BaseShaper implements Shaper {

	private static final String WEBHOOK = "webhook";
	private static final String URL_ATTR = "url";
	private static final String SAVE = "save";
	private WebhookHandler webhookHandler;
	private RequestHandler requestHandler;
	private DirectoryHandler directoryHandler;
	private String webhookId;
	private String url;
	private String path;

	public DistributeShaper(String name, WebhookHandler webhookHandler, RequestHandler requestHandler,
			DirectoryHandler directoryHandler) {
		super(name);
		this.webhookHandler = webhookHandler;
		this.requestHandler = requestHandler;
		this.directoryHandler = directoryHandler;
	}

	@Override
	public void init(JsonObject shapeTemplate, ShaperInitContext context) throws InvalidTemplateException {
		super.init(shapeTemplate, context);
		if (shapeTemplate.has(WEBHOOK)) {
			webhookId = shapeTemplate.get(WEBHOOK).getAsString();
			if (!webhookHandler.isValidWebhookId(webhookId)) {
				throw new InvalidTemplateException("Invalid webhook id: " + webhookId);
			}
		} else if (shapeTemplate.has(URL_ATTR)) {
			url = shapeTemplate.get(URL_ATTR).getAsString();
		} else if (shapeTemplate.has(SAVE)) {
			path = shapeTemplate.get(SAVE).getAsString();
		} else {
			throw new InvalidTemplateException(
					"Please specify the result destination attr (`save`, `url`, `webhook`): " + shapeTemplate);
		}
	}

	@Override
	public void shape(Data data) {
		if (webhookId != null) {
			Webhook webhook = webhookHandler.getWebhook(webhookId);
			webhookHandler.send(webhook, data);
		} else if (url != null) {
			requestHandler.send(url, data);
		} else {
			directoryHandler.save(path, data);
		}
		super.shape(data);
	}
}
