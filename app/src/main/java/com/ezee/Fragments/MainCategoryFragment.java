package com.ezee.Fragments;

/**
 * Created by info3 on 24-07-2017.
 */


        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.ezee.Activity.KnowledgeActivity;
        import com.ezee.Activity.OrderActivity;
        import com.ezee.Activity.ProductActivity;
        import com.ezee.Models.ProductCategory;
        import com.ezee.R;
        import com.ezee.Utils.BaseData;
        import com.ezee.Utils.ConstantValues;
        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

        import developer.shivam.perfecto.OnNetworkRequest;
        import developer.shivam.perfecto.Perfecto;


public class MainCategoryFragment extends Fragment {
    ListView foodItemList;
    List<ProductCategory> Categorylist = new ArrayList<>();
    String[] imageUrl;
    MenuAdapter menuadapter;
    BaseData baseData;
    SharedPreferences preferences;

    public MainCategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        foodItemList = (ListView) view.findViewById(R.id.list_item);
        preferences = this.getActivity().getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);
        getDataFromMenu();

//        foodItemList.setAdapter(new MenuAdapter(getActivity(), Categorylist));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseData = (BaseData) getContext().getApplicationContext();
    }

    private void getDataFromMenu() {
        if(BaseData.getCategorylist().size()==0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", preferences.getString("id",null));
                jsonObject.put("tag", "get_category");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Perfecto.with(getActivity())
                    .fromUrl(ConstantValues.BaseUrl)
                    .ofTypePost(jsonObject)
                    .connect(new OnNetworkRequest() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(String s) {
                            JSONArray docs = null;
                            try {
                                JSONObject responseJson = new JSONObject(s);
                                if (responseJson.getString("msg").contains("successfully")) {
                                    docs = responseJson.getJSONArray("Main_Category");
                                }
                                for (int i = 0; i < docs.length(); i++) {
                                    ProductCategory model = new ProductCategory();
                                    if (docs.getJSONObject(i).has("name"))
                                        model.setProduct_name(docs.getJSONObject(i).getString("name"));
                                    if(docs.getJSONObject(i).has("id"))
                                        model.setId(docs.getJSONObject(i).getString("id"));
                                    if (docs.getJSONObject(i).has("image"))
                                        model.setProduct_image(docs.getJSONObject(i).getString("image"));
                                    Categorylist.add(model);
                                    BaseData.setCategorylist(Categorylist);
                                    foodItemList.setAdapter(new MenuAdapter(getActivity(), Categorylist));
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int i, String s, String s1) {
                            Log.d("Error Msg",s);
                        }
                    });
        }else
        {
            if (menuadapter == null)
                Categorylist=BaseData.getCategorylist();
            foodItemList.setAdapter(new MenuAdapter(getActivity(), Categorylist));

        }

    }

    private class MenuAdapter extends BaseAdapter {
        Context context;
        List<ProductCategory> itemList;

        @Override
        public int getCount() {
            return itemList.size();
        }

        public MenuAdapter(Context con, List<ProductCategory> list) {
            context = con;
            itemList = list;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ProductHolder holder;

            if (convertView == null) {
                holder = new ProductHolder();
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                convertView = inflater.inflate(R.layout.maincategoryitem, null);
                holder.itemName = (TextView) convertView.findViewById(R.id.productText);
                holder.itemImage = (ImageView) convertView.findViewById(R.id.productImage);
                convertView.setTag(holder);


            } else {
                holder = (ProductHolder) convertView.getTag();
            }
            imageUrl = new String[Categorylist.size()];
            String titleCaseValue = Categorylist.get(position).getProduct_name().toString();
            String imageurl = Categorylist.get(position).getProduct_image().toString();
            holder.itemName.setText(titleCaseValue);
            if (imageUrl.length != 0) {
                Picasso.with(getActivity()).load(imageurl).into(holder.itemImage);
            } else {
                Picasso.with(getActivity()).load(R.drawable.placeholder).into(holder.itemImage);
            }

            holder.itemImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(Categorylist.get(position).getProduct_name().contains("Product"))
                    {
                        Intent intent=new Intent(getActivity(),ProductActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else if(Categorylist.get(position).getProduct_name().contains("Order"))
                    {
                        Intent intent=new Intent(getActivity(),OrderActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else if(Categorylist.get(position).getProduct_name().contains("Knowledge"))
                    {
                        Intent intent=new Intent(getActivity(),KnowledgeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }
            });
            return convertView;

        }


    }

    static class ProductHolder {
        TextView itemName;
        ImageView itemImage;

    }


}
