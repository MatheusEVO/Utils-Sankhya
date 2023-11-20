package br.com.evonetwork.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.evonetwork.model.FileInformation;

public class ExtrairFileInformation {
	public FileInformation extrairJson(String contentInfo)  throws IOException {
        String patternString = "__start_fileinformation__(.*?)__end_fileinformation__";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(contentInfo);

        if (matcher.find()) {
            String extractedString = matcher.group(1);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(extractedString, FileInformation.class);
        }
        return null;
    }
}
