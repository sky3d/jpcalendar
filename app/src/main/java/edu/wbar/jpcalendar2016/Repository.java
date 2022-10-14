package edu.wbar.jpcalendar2016;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

public class Repository {

    public static String loadJSON(InputStream is) {
        Writer writer = new StringWriter();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        return writer.toString();
    }


    Model load(InputStream stream, String key) {
        String context = Repository.loadJSON(stream);
        try {
            JSONObject json = new JSONObject(context);
            JSONObject item = json.getJSONObject(key);

            String imageUrl = item.getString("imageurl");
            JSONArray verses = item.getJSONArray("verses");
            JSONObject verse = verses.getJSONObject(0);

            StringBuilder sb = new StringBuilder();

            JSONArray text = verse.getJSONArray("text");

            for (int i = 0; i < text.length(); i++) {
                sb.append(text.getString(i));
                sb.append("\r\n");
            }
            Model model = new Model();
            model.haiku = sb.toString();
            model.published = verse.getString("published");
            model.author = verse.getString("author");
            model.imageUrl = imageUrl;
            return model;

        } catch (JSONException e) {
            return new Model();
        }
    }
}
