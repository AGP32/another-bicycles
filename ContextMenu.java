package agp32.dev;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import agp32.rec.R;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;


public class ContextMenu<Source> extends AlertDialog {

    /**
     * Source of this context menu
     * (e.g. list item that was clicked).
     */
    @Getter private Source source;

    /**
     * @param source {@link #source}
     */
    public ContextMenu(Context context, Source source) {
        super(context);
        this.source = source;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ListView listView = (ListView) getLayoutInflater().inflate(R.layout.contextmenu, null);

        // Set adapter
        if(adapter == null) {
            adapter = new Adapter(getContext());
            adapter.addItems(options);
        }
        listView.setAdapter(adapter);

        // Set listener to trigger options specific listeners
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Option option = adapter.getItem(position);
                option.getOnSelectionListener().call(ContextMenu.this, option);
            }
        });

        // Set view
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setView(listView);
        super.onCreate(savedInstanceState);
    }

    //
    // Options
    //

    @Getter private final List<Option> options = new ArrayList<Option>();

    @Accessors(chain = true)
    @RequiredArgsConstructor(suppressConstructorProperties = true)
    public static class Option {

        private int LAYOUT = R.layout.agp32_contextmenu_item;
        private int TEXT = R.id.agp32_contextmenu_item_textview;
        private int IMAGE = R.id.agp32_contextmenu_item_imageview;

        @Getter private final Integer icon;
        @Getter @NonNull private final Integer text;
        @Getter @NonNull private final OnSelectionListener onSelectionListener;
    }

    public interface OnSelectionListener<Source> {
        public void call(ContextMenu<Source> contextMenu, Option eventSource);
    }

    //
    // List adapter
    //

    private Adapter adapter;

    private static class OptionViewHolder {
        public TextView textView;
        public ImageView imageView;
    }

    private class Adapter extends ListAdapter<Option> {

        public Adapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Option option = getItem(position);

            // Get view if exists or create a new one otherwise

            OptionViewHolder holder;
            View view = convertView;

            if(view == null) {
                holder = new OptionViewHolder();
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(option.LAYOUT, null, true);
                holder.textView = (TextView) view.findViewById(option.TEXT);
                holder.imageView = (ImageView) view.findViewById(option.IMAGE);
                view.setTag(holder);
            } else {
                holder = (OptionViewHolder) view.getTag();
            }

            if(option.getText() != null) {
                holder.textView.setText( option.getText() );
            }
            if(option.getIcon() != null) {
                holder.imageView.setImageResource( option.getIcon() );
            }
            return view;
        }
    }
}
