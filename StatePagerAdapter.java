package agp32.dev;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class StatePagerAdapter<ItemType> extends FragmentStatePagerAdapter {

    public StatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //
    // Items
    //

    private final List<ItemType> items = new ArrayList<ItemType>();

    public void addItem(ItemType item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(ItemType item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    public void addItems(List<ItemType> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(ItemType... items) {
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    //
    // FragmentStatePagerAdapter
    //

    @Override
    public int getCount() {
        return items.size();
    }
}
