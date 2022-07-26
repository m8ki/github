package com.example.git.controller;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.git.model.Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Integrates Github Search API with android interface
 * @param
 * @return
 */
public class GithubAPI {


    private NetworkDAO networkDAO;
    private static ArrayList<Repository> projects;


    public GithubAPI() {
        this.networkDAO = new NetworkDAO();
    }

    public static ArrayList<Repository> getProjects() {
        return projects;
    }

    /**
     * Makes a search in the github api.
     * @param username: user repository
     * @param  minimumUsers: minimum watchers showing
     * @param minimumSize: minimum size showin
     * @param timeSort: user updated/created repositories
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void search(String username, String minimumUsers, String minimumSize, boolean timeSort) throws IOException, JSONException {


        String sort = timeSort ?  "created" : "updated";

        //GITHUB API
        String url = "https://api.github.com/users/" + username +"/repos?sort=" + sort;

        //received response
        String webResponse = networkDAO.request(url);

        //Different Json Objects
        JSONArray jArray = new JSONArray(webResponse);

        //case no repositories
        if (jArray.length()== 0) projects = new ArrayList<>();
        //there are repositories
        else {
            ArrayList<Repository> projectsList= new ArrayList<Repository>();
            //pulling one by one items
            for (int i=0; i < jArray.length(); i++) {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    //values
                    String name = oneObject.getString("name");
                    String language = oneObject.getString("language");
                    Integer size = oneObject.getInt("size");
                    Integer watchers = oneObject.getInt("watchers");
                    Instant created_at = Instant.parse(oneObject.getString("created_at"));
                    Instant last_update = Instant.parse(oneObject.getString("updated_at"));

                    Repository project = new Repository(name, language, size, created_at, last_update, watchers);
                    Log.d("project name", name);
                    Log.d("watchers", Integer.toString(watchers));

                    //if value empty
                    Integer minimumSize_int = 0;
                    Integer minimumUsers_int = 0;
                    try {
                        minimumSize_int = Integer.parseInt(minimumSize);

                    }
                    catch (Exception e) {};

                    try {
                        minimumUsers_int = Integer.parseInt(minimumUsers);
                    }
                    catch (Exception e) {};

                    if (project.getSize() >= minimumSize_int && watchers >= minimumUsers_int) {
                        Log.d ("added", Integer.toString(minimumUsers_int));
                        projectsList.add(project); //if meets requirements
                    }


                } catch (JSONException e) {

                }
            }
            projects = projectsList;
        }




    }
}
