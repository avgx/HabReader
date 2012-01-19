/*
   Copyright (C) 2011 Andrey Zaytsev <a.einsam@gmail.com>
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
  
        http://www.apache.org/licenses/LICENSE-2.0
  
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package net.meiolania.apps.habrahabr.ui.fragments;

import java.io.IOException;
import java.util.ArrayList;

import net.meiolania.apps.habrahabr.Api;
import net.meiolania.apps.habrahabr.adapters.PostsAdapter;
import net.meiolania.apps.habrahabr.data.PostsData;
import net.meiolania.apps.habrahabr.ui.activities.PostsShowActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class PostsDashboardFragment extends ApplicationListFragment{
    protected ArrayList<PostsData> postsDataList = new ArrayList<PostsData>();
    protected PostsAdapter postsAdapter;
    protected int page;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        loadList();
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id){
        PostsData postsData = postsDataList.get(position);

        Intent intent = new Intent(getActivity(), PostsShowActivity.class);
        intent.putExtra("link", postsData.getLink());

        startActivity(intent);
    }

    private void loadList(){
        ++page;
        new LoadPostsList().execute();
    }

    private class LoadPostsList extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params){
            try{
                postsDataList = new Api(getActivity()).getPostsApi().getPosts("http://habrahabr.ru/blogs/page" + page + "/");

                if(postsDataList.isEmpty())
                    postsDataList = new Api(getActivity()).getPostsApi().getPosts("http://habrahabr.ru/blogs/page" + page + "/");
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if(!isCancelled() && page == 1){
                postsAdapter = new PostsAdapter(getActivity(), postsDataList);
                setListAdapter(postsAdapter);
            }else
                postsAdapter.notifyDataSetChanged();
        }

    }

}