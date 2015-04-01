package agp32.dev;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public abstract class SQLiteTable<RowType> {

    protected final SQLiteOpenHelper openHelper;

    public SQLiteTable(SQLiteOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    public abstract String createStatement();

    public abstract String dropStatement();

    //
    // Insert Listeners
    //

    public interface InsertListener<RowType> {
        public void rowsInserted(SQLiteTable<RowType> source, List<RowType> insertedRows);
    }

    public boolean addInsertListener(@NonNull InsertListener<RowType> listener) {
        return getInsertListeners().add(listener);
    }

    public boolean removeInsertListener(@NonNull InsertListener<RowType> listener) {
        return getInsertListeners().remove(listener);
    }

    protected void notifyInsertListeners(List<RowType> insertedRows) {
        for(InsertListener<RowType> listener : getInsertListeners()) {
            listener.rowsInserted(this, insertedRows);
        }
    }

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final List<InsertListener<RowType>> insertListeners =
        new ArrayList<InsertListener<RowType>>();

    //
    // Update Listeners
    //

    public interface UpdateListener<RowType> {
        public void updateListener(SQLiteTable<RowType> source,
                                   List<RowType> oldRows, List<RowType> updatedRows);
    }

    public boolean addUpdateListener(@NonNull UpdateListener<RowType> listener) {
        return getUpdateListeners().add(listener);
    }

    public boolean removeUpdateListener(@NonNull UpdateListener<RowType> listener) {
        return getUpdateListeners().remove(listener);
    }

    protected void notifyUpdateListeners(List<RowType> oldRows, List<RowType> updatedRows) {
        for(UpdateListener<RowType> listener : getUpdateListeners()) {
            listener.updateListener(this, oldRows, updatedRows);
        }
    }

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final List<UpdateListener<RowType>> updateListeners =
        new ArrayList<UpdateListener<RowType>>();

    //
    // Delete listeners
    //

    public interface DeleteListener<RowType> {
        public void rowsDeleted(SQLiteTable<RowType> source, List<RowType> deletedRows);
    }

    public void addDeleteListener(@NonNull DeleteListener<RowType> listener) {
        getDeleteListeners().add(listener);
    }

    public void removeDeleteListener(@NonNull DeleteListener<RowType> listener) {
        getDeleteListeners().remove(listener);
    }

    protected void notifyDeleteListeners(List<RowType> deletedRows) {
        for(DeleteListener<RowType> listener : getDeleteListeners()) {
            listener.rowsDeleted(this, deletedRows);
        }
    }

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final List<DeleteListener<RowType>> deleteListeners =
        new ArrayList<DeleteListener<RowType>>();

    //
    // Invalidate listeners
    //

    public interface InvalidationListener {
        public void invalidated(SQLiteTable source);
    }

    public boolean addInvalidationListener(@NonNull InvalidationListener listener) {
        return getInvalidationListeners().add(listener);
    }

    public boolean removeInvalidationListener(@NonNull InvalidationListener listener) {
        return getInvalidationListeners().remove(listener);
    }

    protected void notifyInvalidationListeners() {
        for(InvalidationListener listener : getInvalidationListeners()) {
            listener.invalidated(this);
        }
    }

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final List<InvalidationListener> invalidationListeners =
        new ArrayList<InvalidationListener>();
}


