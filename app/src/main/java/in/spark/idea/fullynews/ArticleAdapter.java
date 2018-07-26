package in.spark.idea.fullynews;

/**
 * Created by Kanagasabapathi on 7/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import in.spark.idea.fullynews.FullyParser.Parser;
import in.spark.idea.fullynews.FullyParser.Article;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArrayList<Article> articles;

    private int rowLayout;
    private Context mContext;
    WebView articleView;

    private int AD_TYPE=1, NORMAL=2;
    private boolean mIsAd;

    public ArticleAdapter(ArrayList<Article> list, int rowLayout, Context context) {
        this.articles = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    @Override
    public long getItemId(int item) {
        // TODO Auto-generated method stub
        return item;
    }
    public void clearData(){
        if (articles != null)
            articles.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        if(i==AD_TYPE){
            mIsAd=true;
        }else{
            mIsAd=false;
        }

        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if(position>0 && position%5==0)
            return AD_TYPE;
        return NORMAL;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)  {
//        if(mIsAd){
//            MobileAds.initialize(mContext, "ca-app-pub-4283164631469341~4786268306");

//            viewHolder.adView.setVisibility(View.VISIBLE);
//            viewHolder.mNewsRow.setVisibility(View.GONE);
//
//            viewHolder.adView.setAdSize(AdSize.BANNER);
//            viewHolder.adView.setAdUnitId(mContext.getString(R.string.banner_bottom_ad_unit_id));
//
//            AdRequest adTopRequest = new AdRequest.Builder()
//                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                    .build();
//
//            viewHolder.adView.loadAd(adTopRequest);

//        }else {

//            viewHolder.adView.setVisibility(View.GONE);
//            viewHolder.mNewsRow.setVisibility(View.VISIBLE);


            Article currentArticle = articles.get(position);

            Locale.setDefault(Locale.getDefault());
            Date date = currentArticle.getPubDate();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
            final String pubDateString = sdf.format(date);

            viewHolder.title.setText(currentArticle.getTitle());

            //load the image. If the parser did not find an image in the article, it set a placeholder.
            Picasso.with(mContext)
                    .load(currentArticle.getImage())
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.image);

            viewHolder.pubDate.setText(pubDateString);


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //show article content inside a dialog
                    articleView = new WebView(mContext);

                    articleView.getSettings().setLoadWithOverviewMode(true);

                    String url = articles.get(position).getLink();

                    Intent mIntent = new Intent(mContext, Reader.class);
                    mIntent.putExtra("link", url);
                    mContext.startActivity(mIntent);
                }
            });
//        }
    }

    @Override
    public int getItemCount() {

        return articles == null ? 0 : articles.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView pubDate;
        ImageView image;
        RelativeLayout mNewsRow;
        AdView adView;

        public ViewHolder(View itemView) {

            super(itemView);

            adView = (AdView)itemView.findViewById(R.id.rownative);
            mNewsRow = (RelativeLayout)itemView.findViewById(R.id.newsrow);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            image = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}
