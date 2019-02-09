package com.zeneo.newsapp.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zeneo.newsapp.Models.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GetDetailsFromApi {


    private String url;
    private String type;
    private String RecyvlerView;
    List<Movies> list;
    private ImageView bd,pr;
    private TextView tt,st,rl,olg,rt,bg,gr,rv,desc;
    Context context;

    public GetDetailsFromApi(ImageView bd, ImageView pr, TextView tt, TextView st, TextView rl, TextView olg, TextView rt, TextView bg, TextView gr, TextView rv) {
        this.bd = bd;
        this.pr = pr;
        this.tt = tt;
        this.st = st;
        this.rl = rl;
        this.olg = olg;
        this.rt = rt;
        this.bg = bg;
        this.gr = gr;
        this.rv = rv;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void applique(){
        new getDetails().execute();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public class getDetails extends AsyncTask<Void ,Void ,Void > {
        HttpHandler sh = new HttpHandler();
        String tt=null,st=null,rl=null,olg=null,rt=null,bg=null,gr = null,rv=null,bd=null,pr=null,desc=null;
        @Override
        protected Void doInBackground(Void... voids) {

            String jsonStr = sh.makeServiceCall(url);

            try {
                JSONObject ob = new JSONObject(jsonStr);
                bd = "https://image.tmdb.org/t/p/original"+ob.getString("backdrop_path");
                pr = "https://image.tmdb.org/t/p/w500"+ob.getString("poster_path");
                tt = ob.getString("title");
                st = ob.getString("status");
                rl = ob.getString("release_date");
                olg = ob.getString("original_language");
                rt = new NumToTime().converter(ob.getInt("runtime"));
                bg = "$"+ String.valueOf(ob.getInt("budget"));
                for (int i = 0 ; i<ob.getJSONArray("genres").length();i++){
                    JSONArray grarr = ob.getJSONArray("genres");
                    gr = grarr.getJSONObject(i).getString("name");
                }
                rv = "$" + String.valueOf(ob.getInt("revenue"));
                Log.i("TAG",bd);
                desc = ob.getString("overview");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Glide.with(context).load(bd)
                    .into(GetDetailsFromApi.this.bd);
            Glide.with(context).load(pr).apply(RequestOptions.centerCropTransform().error(com.zeneo.newsapp.R.drawable.bg_null))
                    .into(GetDetailsFromApi.this.pr);
            GetDetailsFromApi.this.tt.setText(GetDetailsFromApi.this.tt.getText()+tt);
            GetDetailsFromApi.this.st.setText(GetDetailsFromApi.this.st.getText()+st);
            GetDetailsFromApi.this.rl.setText(GetDetailsFromApi.this.rl.getText()+rl);
            GetDetailsFromApi.this.olg.setText(GetDetailsFromApi.this.olg.getText()+olg);
            GetDetailsFromApi.this.rt.setText(GetDetailsFromApi.this.tt.getText()+tt);
            GetDetailsFromApi.this.bg.setText(GetDetailsFromApi.this.rt.getText()+rt);
            GetDetailsFromApi.this.gr.setText(GetDetailsFromApi.this.gr.getText()+gr);
            GetDetailsFromApi.this.rv.setText(GetDetailsFromApi.this.rv.getText()+rv);

        }
    }





}
