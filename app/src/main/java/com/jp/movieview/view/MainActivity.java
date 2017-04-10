package com.jp.movieview.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jp.movieview.R;
import com.jp.movieview.adapter.MainAdapter;
import com.jp.movieview.bean.MovieBean;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.utils.LogUtils;

import com.jp.movieview.utils.ToastUtils;
import com.lzy.okgo.OkGo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BaseQuickAdapter.RequestLoadMoreListener {

    public static final String TAG="MainActivity";

    RecyclerView mRecyclerView;


    int page=1;
    MainAdapter adapter;
    boolean isErr=true;
    int mCurrentCounter;

    private static int TOTAL_COUNTER = 0;

    String name="你的名字";

    private static final int PAGE_SIZE = 10;
    DrawerLayout drawer;
    protected static final String EXTRA_KEY_TEXT = "text";
    private static final String EXTRA_KEY_VERSION = "version";
    private static final String EXTRA_KEY_THEME = "theme";
    private static final String EXTRA_KEY_VERSION_MARGINS = "version_margins";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);



        mRecyclerView= (RecyclerView) findViewById(R.id.recycler);



        final List<MovieBean> list=new ArrayList<>();

        adapter=new MainAdapter();
        adapter.setOnLoadMoreListener(this,mRecyclerView);
        final LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this);
        OkGo.get("http://www.diaosisou.com/list/"+name+"/"+page)
                .tag(this)
                .execute(new JsonCallBack<String>(MainActivity.this) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        Date time1=new Date();
                        long time2 = time1.getTime();
                        Document doc = Jsoup.parse(result);
                        //Elements company = doc.select("a[href]");
                        Elements company = doc.select("div.T1");
                        String title;
                        String uri;

                        for(Element e : company) {

                            title = e.select("a").text();
                            uri = e.select("a").attr("href");
                            LogUtils.e(TAG,title+uri);
                            list.add(new MovieBean(title,uri));

                        }

                        List<String[]> xiangxi=new ArrayList<>();

                        Elements canshu = doc.select("dl.BotInfo");
                        for(Element e : canshu) {
                            String s1=e.select("dt").text();
                            s1.replace(" ","");
                            String[] split = s1.split(" ");

                            xiangxi.add(split);
                            LogUtils.e(TAG,s1);
                        }

                        for (int i=0;i<xiangxi.size();i++){
                            list.get(i).setMovieSize(xiangxi.get(i)[1]);
                            list.get(i).setMovieNum(xiangxi.get(i)[4]);
                            list.get(i).setMovieTime(xiangxi.get(i)[7]);
                            list.get(i).setMovieHot(xiangxi.get(i)[10]);
                        }

                        Elements num = doc.select("div.rststat");
                        for(Element e : num) {
                            LogUtils.e(TAG,getNumPattern(e.text()));
                            ToastUtils.showToast(MainActivity.this,getNumPattern(e.text()));
                            TOTAL_COUNTER=Integer.parseInt(getNumPattern(e.text()));
                        }

                        List<String> magnet=new ArrayList<String>();
                        Elements lianjie = doc.select("div.dInfo");
                        for(Element e : lianjie) {
                            String s1=e.select("a").attr("href");
                            magnet.add(s1);
                            LogUtils.e(TAG,s1);

                            //LogUtils.e(TAG,e.text());
                        }


                        Elements resultLinks = doc.select("[href^=thunder]");
                        for(Element e : resultLinks) {
                            String s1=e.select("a").attr("href");

                            LogUtils.e(TAG,s1);

                            //LogUtils.e(TAG,e.text());
                        }

                        for (int i=0;i<magnet.size();i++){
                            list.get(i).setMovieMagnet(magnet.get(i));
                        }



                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setLayoutManager(manager);
                        //adapter=new MainAdapter(list);
                        adapter.setNewData(list);
                        //adapter.openLoadAnimation(new RecyclerAnimation());
                        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                        mRecyclerView.setAdapter(adapter);
                        Date time3=new Date();
                        long time4 = time3.getTime();
                        LogUtils.e(TAG,"时间"+(time4-time2));

                        mCurrentCounter = adapter.getData().size();

//                        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//                            @Override
//                            public void onLoadMoreRequested() {
//
//
//                            }
//                        },mRecyclerView);


                    }
                });



//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int totalItemCount = recyclerView.getAdapter().getItemCount();
//                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
//                int visibleItemCount = recyclerView.getChildCount();
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItemPosition == totalItemCount - 1
//                        && visibleItemCount > 0) {
//                    page++;
//                    OkGo.get("http://www.diaosisou.com/list/金刚狼/"+page)
//                            .execute(new JsonCallBack<String>(MainActivity.this) {
//                                @Override
//                                public void onSuccess(String result, Call call, Response response) {
//                                    Document doc = Jsoup.parse(result);
//                                    //Elements company = doc.select("a[href]");
//                                    Elements company = doc.select("div.T1");
//
//
//                                    for(Element e : company) {
//
//                                        String title = e.select("a").text();
//                                        String uri = e.select("a").attr("href");
//                                        list.add(new MovieBean(title,uri));
//                                    }
//                                    adapter.notifyDataSetChanged();
//                                }
//                            });
//                }
//
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);

        //getMenuInflater().inflate(R.menu.search, menu);
//
//        MenuItem item = menu.findItem(R.id.action_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.menu_search:
//                mSearchView.open(true, item);
//
//                return true;
//            /* case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START); finish();
//                return true;*/
//            default:
//                return super.onOptionsItemSelected(item);
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    List<MovieBean> getData(int page){
        final List<MovieBean> list1=new ArrayList<MovieBean>();

        LogUtils.e("jiang",page+"page-------------------");
        OkGo.get("http://www.diaosisou.com/list/"+name+"/"+page)
                .tag(this)
                .execute(new JsonCallBack<String>(MainActivity.this) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        Document doc = Jsoup.parse(result);
                        //Elements company = doc.select("a[href]");
                        Elements company = doc.select("div.T1");


                        for(Element e : company) {

                            String title = e.select("a").text();
                            String uri = e.select("a").attr("href");
                            title.replace("@","\\@");
                            list1.add(new MovieBean(title,uri));
                        }

                        List<String[]> xiangxi=new ArrayList<>();

                        Elements canshu = doc.select("dl.BotInfo");
                        for(Element e : canshu) {
                            String s1=e.select("dt").text();
                            s1.replace(" ","");
                            String[] split = s1.split(" ");

                            xiangxi.add(split);
                            LogUtils.e("jiang",s1+"--------大小");
                        }

                        for (int i=0;i<xiangxi.size();i++){
                            list1.get(i).setMovieSize(xiangxi.get(i)[1]);
                            list1.get(i).setMovieNum(xiangxi.get(i)[4]);
                            list1.get(i).setMovieTime(xiangxi.get(i)[7]);
                            list1.get(i).setMovieHot(xiangxi.get(i)[10]);
                        }

                        adapter.addData(list1);
                        mCurrentCounter = adapter.getData().size();
                        adapter.loadMoreComplete();
                    }
                });

//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));
//        list1.add(new MovieBean("aaa","bbb"));


        return list1;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);



    }

    @Override
    public void onLoadMoreRequested() {
        //if(mCurrentCounter>=20){
//        if(page==2){
//            adapter.loadMoreEnd(true);
//        }else {
//            if (isErr) {
//                //成功获取更多数据
//
//                adapter.addData(getData(2));
//                mCurrentCounter = adapter.getData().size();
//                adapter.loadMoreComplete();
//            } else {
//                //获取更多数据失败
//                isErr = true;
//                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//                adapter.loadMoreFail();
//
//            }
//        }


        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(false);

        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {

//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                adapter.loadMoreEnd(false);//true is gone,false is visible
            } else {
                if (isErr) {
                    page+=1;
                    getData(page);
                } else {
                    isErr = true;
                    Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_LONG).show();
                    adapter.loadMoreFail();

                }
            }
        }
    }

    String getNum(String str){
        List<String> digitList = new ArrayList<>();
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(str);
        String result = m.replaceAll("");
        for (int i = 0; i < result.length(); i++) {
            digitList.add(result.substring(i, i+1));
        }
        return digitList.toString();
    }

    String getNumPattern(String str){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
