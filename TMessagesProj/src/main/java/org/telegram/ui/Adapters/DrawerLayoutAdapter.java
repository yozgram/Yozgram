/*
 * This is the source code of Telegram for Android v. 3.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2017.
 */

package org.telegram.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.readystatesoftware.viewbadger.BadgeView;

import org.telegram.Adel.Setting;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DrawerActionCell;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.DrawerProfileCell;
import org.telegram.ui.Components.RecyclerListView;

import java.util.ArrayList;

public class DrawerLayoutAdapter extends RecyclerListView.SelectionAdapter {

    private Context mContext;
    public DrawerLayoutAdapter(Context context) {
        mContext = context;
        Theme.createDialogsResources(context);
        resetItems();
    }

    private ArrayList<Item> items = new ArrayList<>(29);

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void notifyDataSetChanged() {
        resetItems();
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(RecyclerView.ViewHolder holder) {
        return holder.getItemViewType() == 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new DrawerProfileCell(mContext);
                break;
            case 1:
            default:
                view = new EmptyCell(mContext, AndroidUtilities.dp(8));
                break;
            case 2:
                view = new DividerCell(mContext);
                break;
            case 3:
                view = new DrawerActionCell(mContext);
                break;
        }
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new RecyclerListView.Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ((DrawerProfileCell) holder.itemView).setUser(MessagesController.getInstance().getUser(UserConfig.getClientUserId()));
                holder.itemView.setBackgroundColor(Theme.getColor(Theme.key_avatar_backgroundActionBarBlue));
                break;
            case 3:
                items.get(position).bind((DrawerActionCell) holder.itemView);
                break;
        }
    }

    @Override
    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        } else if (i == 1) {
            return 1;
        } else if (i == 4 || i == 11 || i == 17 || i == 23 || i == 28) {
            return 2;
        }
        return 3;
    }

    private void resetItems() {
        items.clear();
        if (!UserConfig.isClientActivated()) {
            return;
        }

        boolean hiddenMode = Setting.GetHiddenMode(); // Adel

        items.add(null); // profile
        items.add(null); // padding
        items.add(new Item(17, LocaleController.getString("AddNewAccount", R.string.AddNewAccount), R.drawable.ic_person_add_white_24dp));
        items.add(new Item(18, LocaleController.getString("ChangeAccount", R.string.ChangeAccount), R.drawable.ic_people_white_24dp));
        items.add(null); // divider 4 (4 is index not id)
        items.add(new Item(2, LocaleController.getString("NewGroup", R.string.NewGroup), R.drawable.menu_newgroup));
        items.add(new Item(3, LocaleController.getString("NewSecretChat", R.string.NewSecretChat), R.drawable.menu_secret));
        items.add(new Item(4, LocaleController.getString("NewChannel", R.string.NewChannel), R.drawable.menu_broadcast));
        items.add(new Item(10, LocaleController.getString("Calls", R.string.Calls), R.drawable.menu_calls));
        items.add(new Item(29, LocaleController.getString("FavoriteChannels", R.string.FavoriteChannels), R.drawable.ic_star_white_24dp));
	    items.add(new Item(24, hiddenMode ? LocaleController.getString("NormalChats", R.string.NormalChats) : LocaleController.getString("HiddenChats", R.string.HiddenChats), hiddenMode ? R.drawable.ic_visibility_white_24dp : R.drawable.ic_visibility_off_white_24dp));
        items.add(null); // divider 11
        items.add(new Item(6, LocaleController.getString("Contacts", R.string.Contacts), R.drawable.menu_contacts));
        items.add(new Item(22, LocaleController.getString("OnlineContacts", R.string.OnlineContacts), R.drawable.ic_mood_white_24dp));
        items.add(new Item(21, LocaleController.getString("DeleteContacts", R.string.DeleteContacts), R.drawable.ic_ab_delete));
        items.add(new Item(27, LocaleController.getString("contactChanges", R.string.contactChanges), R.drawable.ic_contacts_white_24dp));
        items.add(new Item(28, LocaleController.getString("SpecificContacts", R.string.SpecificContacts), R.drawable.ic_favorite_white_24dp));
        items.add(null); // divider 17
        items.add(new Item(14, LocaleController.getString("Themes", R.string.Themes), R.drawable.ic_color_lens_white_24dp));
        items.add(new Item(15, LocaleController.getString("SelectFont", R.string.SelectFont), R.drawable.ic_font_download_white_24dp));
        items.add(new Item(26, LocaleController.getString("voicechanger", R.string.voicechanger), R.drawable.ic_record_voice_over_white_24dp));
        items.add(new Item(8, LocaleController.getString("Settings", R.string.Settings), R.drawable.menu_settings));
        items.add(new Item(23, LocaleController.getString("MySettings", R.string.MySettings), R.drawable.ic_settings_applications_white_24dp));
        items.add(null); // divider 23
        items.add(new Item(25, LocaleController.getString("AllMedias", R.string.AllMedias), R.drawable.ic_live_tv_white_24dp));
        items.add(new Item(19, LocaleController.getString("IdFinderTitle", R.string.IdFinderTitle), R.drawable.ic_find_in_page_white_24dp));
        items.add(new Item(20, LocaleController.getString("CacheCleaner", R.string.CacheCleaner), R.drawable.chats_clear));
        items.add(new Item(12, LocaleController.getString("ReportElimination", R.string.ReportElimination), R.drawable.ic_block_white_24dp));
	    items.add(null); // divider 28
        items.add(new Item(16, LocaleController.getString("Comment", R.string.Comment), R.drawable.ic_comment_white_24dp));
        items.add(new Item(13, LocaleController.getString("AppChannel", R.string.AppChannel), R.drawable.menu_broadcast));
        items.add(new Item(7, LocaleController.getString("InviteFriends", R.string.InviteFriends), R.drawable.ic_share_white_24dp));
        items.add(new Item(9, LocaleController.getString("TelegramFaq", R.string.TelegramFaq), R.drawable.menu_help));
    }

    public int getId(int position) {
        if (position < 0 || position >= items.size()) {
            return -1;
        }
        Item item = items.get(position);
        return item != null ? item.id : -1;
    }

    private class Item {
        public int icon;
        public String text;
        public int id;

        public Item(int id, String text, int icon) {
            this.icon = icon;
            this.id = id;
            this.text = text;
        }

        public void bind(DrawerActionCell actionCell) {
            String newText = text; // Adel
            if (id == 27) { // Adel
                Integer count = mContext.getSharedPreferences(ApplicationLoader.instance_number + "mainconfig", Activity.MODE_PRIVATE).getInt("ContactChangesCount", 0);
                if (count > 0) {
                    newText = text + " (" + count + ")";
                }
            }

            actionCell.setTextAndIcon(newText, icon);
        }
    }
}
