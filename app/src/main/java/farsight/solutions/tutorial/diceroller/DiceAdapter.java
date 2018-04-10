package farsight.solutions.tutorial.diceroller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class DiceAdapter extends ArrayAdapter<Dice> {
    public DiceAdapter(@NonNull Context context, int resource, List<Dice> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dice_row, parent, false);
        }

        //setup dice image
        ImageView imageView = convertView.findViewById(R.id.dice_icon);
        Dice dice = this.getItem(position);
        switch (dice.diceVal) {
            case BLANK:
                imageView.setImageResource(R.drawable.blank_dice);
                break;
            case MAGNIFY:
                imageView.setImageResource(R.drawable.magnifying_glass);
                break;
            case STAR:
                imageView.setImageResource(R.drawable.star);
                break;
        }

        //setup dice hold button
        Button holdButton = convertView.findViewById(R.id.dice_hold_button);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dice dice = getItem(position);
                dice.toggleHold();
            }
        });

        //setup dice change button
        Button changeButton = convertView.findViewById(R.id.dice_change_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dice dice = getItem(position);
                dice.nextValue();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}