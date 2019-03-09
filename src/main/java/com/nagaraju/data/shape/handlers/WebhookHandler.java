package com.nagaraju.data.shape.handlers;

import com.nagaraju.data.shape.core.Data;

public interface WebhookHandler {

	Webhook getWebhook(String id);

	boolean isValidWebhookId(String webhookId);

	void send(Webhook webhook, Data data);
}
