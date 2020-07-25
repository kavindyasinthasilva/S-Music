package com.droidheat.musicplayerplus.ui.fragments;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.commonsware.cwac.merge.MergeAdapter;
import com.droidheat.musicplayerplus.utils.ImageUtils;
import com.droidheat.musicplayerplus.ui.views.NoScrollListView;
import com.droidheat.musicplayerplus.R;
import com.droidheat.musicplayerplus.utils.SharedPrefsUtils;
import com.droidheat.musicplayerplus.models.SongModel;
import com.droidheat.musicplayerplus.utils.SongsUtils;
import com.droidheat.musicplayerplus.ui.activities.GlobalDetailActivity;
import com.droidheat.musicplayerplus.ui.adapters.AdapterFiveRecentlyAdded;
import com.droidheat.musicplayerplus.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeFragment extends Fragment {

    SongsUtils songsUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        songsUtils = new SongsUtils(getActivity());

        ListView listView = view.findViewById(R.id.listView);

        MergeAdapter mergeAdapter = new MergeAdapter();

        if (!songsUtils.allSongs().isEmpty()) {

            mergeAdapter.addView(threeGridView());

            // One Big Two Small



            // Recently Added
            View heading1 = View.inflate(getActivity(), R.layout.heading_with_button, null);
            TextView textView1 = heading1.findViewById(R.id.titleTextView);
            textView1.setText("Recently Added To Library");
            Button button = heading1.findViewById(R.id.button);
            button.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GlobalDetailActivity.class);
                    intent.putExtra("name", "Recently Added");
                    intent.putExtra("field", "recent");
                    startActivity(intent);
                }
            });

            button.setTextColor(ContextCompat.getColor(getActivity(),
                    (new CommonUtils(getActivity())).accentColor(new SharedPrefsUtils(getActivity()))));
            button.setText("View All");
            mergeAdapter.addView(heading1);

            View recent_list = View.inflate(getActivity(), R.layout.scroll_disabled_list_view, null);
            NoScrollListView listView1 = recent_list.findViewById(R.id.scroll_disabled_list_view);
            listView1.setExpanded(true);
            AdapterFiveRecentlyAdded adapterFiveRecentlyAdded = new AdapterFiveRecentlyAdded(getActivity());
            listView1.setAdapter(adapterFiveRecentlyAdded);

            mergeAdapter.addView(recent_list);
        } else {
            View heading = View.inflate(getActivity(), R.layout.heading, null);
            TextView textView = heading.findViewById(R.id.titleTextView);
            textView.setText("Unable to find any music in your device. if you have just added music then click on top right options" +
                    " icon and try 'Sync Music'");
            mergeAdapter.addView(heading);
        }

        // Setting Adapter

        listView.setAdapter(mergeAdapter);

        return view;
    }




    private View threeGridView() {
        View three_grid = View.inflate(getActivity(),R.layout.home_three_grid,null);
        ImageView imageView1 = three_grid.findViewById(R.id.home_three_grid_imageView_1);
        ImageView imageView2 = three_grid.findViewById(R.id.home_three_grid_imageView_2);
        ImageView imageView3 = three_grid.findViewById(R.id.home_three_grid_imageView_3);
        TextView textView1 = three_grid.findViewById(R.id.home_three_grid_textView_1);
        TextView textView2 = three_grid.findViewById(R.id.home_three_grid_textView_2);
        TextView textView3 = three_grid.findViewById(R.id.home_three_grid_textView_3);

        imageView1.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 40F);
            }
        });
        imageView1.setClipToOutline(true);
        imageView2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 40F);
            }
        });
        imageView3.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 40F);
            }
        });
        imageView3.setClipToOutline(true);
        imageView2.setClipToOutline(true);
        if (songsUtils.allSongs().size() > 0) {
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.app_shuffle_inactive));
            imageView1.setPadding(76,76,76,76);
            textView1.setText("Shuffle All");
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songsUtils.shufflePlay(songsUtils.allSongs());
                }
            });
            if (songsUtils.mostPlayedSongs().size() > 0) {
                (new ImageUtils(getActivity())).setAlbumArt(songsUtils.mostPlayedSongs(),imageView2);
                textView2.setText("Most Played");
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        songsUtils.play(0, songsUtils.mostPlayedSongs());
                    }
                });
                if (songsUtils.favouriteSongs().size() > 0) {
                    (new ImageUtils(getActivity())).setAlbumArt(songsUtils.favouriteSongs(),imageView3);
                    textView3.setText("Favorites");

                    imageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            songsUtils.play(0, songsUtils.favouriteSongs());
                        }
                    });
                } else {
                    (new ImageUtils(getActivity())).setAlbumArt(songsUtils.newSongs(),imageView3);
                    textView3.setText("Recently Added");
                    imageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            songsUtils.play(0, songsUtils.newSongs());
                        }
                    });
                }
            } else {
                (new ImageUtils(getActivity())).setAlbumArt(songsUtils.newSongs(),imageView2);
                textView2.setText("Recently Added");
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        songsUtils.play(0, songsUtils.newSongs());
                    }
                });
                imageView3.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
            }
        } else {
            three_grid.setVisibility(View.GONE);
        }
        return three_grid;
    }
}
