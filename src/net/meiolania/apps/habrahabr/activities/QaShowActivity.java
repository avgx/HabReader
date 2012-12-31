/*
Copyright 2012 Andrey Zaytsev

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

package net.meiolania.apps.habrahabr.activities;

import net.meiolania.apps.habrahabr.R;
import net.meiolania.apps.habrahabr.fragments.qa.QaCommentsFragment;
import net.meiolania.apps.habrahabr.fragments.qa.QaShowFragment;
import net.meiolania.apps.habrahabr.ui.TabListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;

public class QaShowActivity extends AbstractionActivity
{
	public final static String EXTRA_URL = "url";
	public final static String EXTRA_TITLE = "title";
	private String title;
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		loadExtras();

		showActionBar();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:
				Intent intent = new Intent(this, QaActivity.class);
				if(NavUtils.shouldUpRecreateTask(this, intent))
				{
					TaskStackBuilder.create(this).addNextIntent(intent).startActivities();
					finish();
				}else
					NavUtils.navigateUpTo(this, intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadExtras()
	{
		url = getIntent().getStringExtra(EXTRA_URL);
		title = getIntent().getStringExtra(EXTRA_TITLE);
	}

	private void showActionBar()
	{
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(title);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		/*
		 * Question tab
		 */
		Bundle arguments = new Bundle();
		arguments.putString(QaShowFragment.URL_ARGUMENT, url);

		Tab tab = actionBar.newTab().setText(R.string.question).setTag("question")
				.setTabListener(new TabListener<QaShowFragment>(this, "question", QaShowFragment.class, arguments));
		actionBar.addTab(tab);

		/*
		 * Comments tab
		 */
		arguments = new Bundle();
		arguments.putString(QaCommentsFragment.URL_ARGUMENT, url);

		tab = actionBar.newTab().setText(R.string.comments).setTag("comments")
				.setTabListener(new TabListener<QaCommentsFragment>(this, "comments", QaCommentsFragment.class, arguments));
		actionBar.addTab(tab);
	}

	@Override
	protected OnClickListener getConnectionDialogListener()
	{
		return new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
			}
		};
	}

}