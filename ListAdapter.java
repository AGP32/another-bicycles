package agp32.dev;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class ListAdapter<ItemType> extends BaseAdapter {

    @Getter @NonNull private final Context context;

    //
    // Items
    //

    private List<ItemType> items = new ArrayList<ItemType>();

    public void addItem(ItemType item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(ItemType... items) {
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    public void addItems(List<ItemType> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void removeItem(ItemType item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    public void removeItems(ItemType... items) {
        this.items.removeAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    public void removeItems(List<ItemType> items) {
        this.items.removeAll(items);
        notifyDataSetChanged();
    }

    //
    // Comparator
    //

    @Getter @Setter @NonNull
    protected Comparator<ItemType> comparator = new Comparator<ItemType>() {
        @Override
        public int compare(ItemType item1, ItemType item2) {
            String str1 = item1.toString();
            String str2 = item2.toString();
            return str1.compareTo(str2);
        }
    };

    /**
     * Sort this list's genres with the help of {@link #comparator}.
     */
    public void sort() {
        Collections.sort(items, comparator);
    }

    //
    // BaseAdapter implementation
    //

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ItemType getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
