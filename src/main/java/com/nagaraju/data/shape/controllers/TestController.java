package com.nagaraju.data.shape.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nagaraju.data.shape.core.Data;
import com.nagaraju.data.shape.core.Template;
import com.nagaraju.data.shape.shapers.InvalidTemplateException;
import com.nagaraju.data.shape.shapers.Shaper;
import com.nagaraju.data.shape.shapers.ShaperBuilder;

@RestController
public class TestController {

	@Autowired
	private ShaperBuilder shaperBuilder;

	@PostMapping("/shape")
	public @ResponseBody String shape(@RequestBody String body) throws InvalidTemplateException {
		JsonParser jsonParser = new JsonParser();
		JsonObject requestData = (JsonObject) jsonParser.parse(body);
		JsonArray data = requestData.getAsJsonArray("data");
		JsonArray template = requestData.getAsJsonArray("template");
		Shaper shaper = shaperBuilder.build(new Template(template));
		shaper.shape(new Data(null, data));
		return "Ok";
	}

	@PostMapping("/print")
	public @ResponseBody String print(@RequestBody String body) {
		System.out.println(body);
		return "Ok";
	}
}
