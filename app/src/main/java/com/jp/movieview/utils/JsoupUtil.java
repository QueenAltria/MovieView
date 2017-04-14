package com.jp.movieview.utils;

import com.jp.movieview.bean.YandeBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.jp.movieview.R.id.size;

/**
 * Created by jp on 2017/4/13
 */
public class JsoupUtil {
    public static final String TAG="JsoupUtil";

    public static List<YandeBean> parseYandeData(String result){
        List<YandeBean> list=new ArrayList<>();
        Document doc = Jsoup.parse(result);
        //Elements company = doc.select("a[href]");
        Elements select = doc.select("div.inner");
        Elements sizes = doc.select("span.directlink-res");
        Elements real_size_url=doc.select("a.directlink");
        for (int i = 0; i < select.size(); i++) {
            // 跳转链接
            String src_url = select.get(i).select("a").attr("href");
            // 预览图
            String preview_url = select.get(i).select("img").attr("src");
            // UserName
            String tags = select.get(i).select("img").attr("title");
            int user = tags.indexOf("User");
            int someTag=tags.indexOf("Tags");
            String userName = tags.substring(user + 5, tags.length());
            //TAG
            String tagList=tags.substring(someTag+5,user).trim();
            // 尺寸
            String size = sizes.get(i).text();
            //width
            String width = select.get(i).select("img").attr("width");
            //height
            String height = select.get(i).select("img").attr("height");
            //真实图片地址
            String real =real_size_url.get(i).select("a").attr("href");

            //LogUtils.e(TAG,"real"+real);
            LogUtils.e(TAG,preview_url);

            YandeBean safebooruBean = new YandeBean();
            safebooruBean.setPreview_url(preview_url);
            safebooruBean.setSize(size);
            safebooruBean.setName(userName);
            safebooruBean.setSrc_url(src_url);
            safebooruBean.setWidth(Integer.parseInt(width));
            safebooruBean.setHeight(Integer.parseInt(height));
            safebooruBean.setRealUrl(real);
            ArrayList<String> tag=new ArrayList<>();
            String[] split = tagList.split(" ");
            for (int j=0;j<split.length;j++){
                tag.add(split[j]);
            }
            safebooruBean.setTagList(tag);


            list.add(safebooruBean);
        }
        return list;
    }
}
