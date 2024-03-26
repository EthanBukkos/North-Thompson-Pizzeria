package com.ethanlogintest.order_customization;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToppingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<SauceDataModel> sauceList;
    private final List<MeatDataModel> meatList;
    private final List<VeggieDataModel> veggieList;
    private final ToppingSelectionListener toppingSelectionListener;

    public ToppingAdapter(
            List<SauceDataModel> sauceList,
            List<MeatDataModel> meatList,
            List<VeggieDataModel> veggieList,
            ToppingSelectionListener toppingSelectionListener) {

        this.sauceList = sauceList;
        this.meatList = meatList;
        this.veggieList = veggieList;
        this.toppingSelectionListener = toppingSelectionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        switch (viewType) {
            case 0:
                itemView = inflater.inflate(R.layout.sauce_row, parent, false);
                return new ViewHolder1(itemView);
            case 1:
                itemView = inflater.inflate(R.layout.meat_row, parent, false);
                return new ViewHolder2(itemView);
            case 2:
                itemView = inflater.inflate(R.layout.veggie_row, parent, false);
                return new ViewHolder3(itemView);
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position < sauceList.size()) {
            return 0;
        } else if (position < sauceList.size() + meatList.size()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {
            case 0: // Sauce selection
                SauceDataModel sauce = sauceList.get(position);
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                viewHolder1.bindData(sauce);

                viewHolder1.sauceTextView.setText(sauce.getSauce());
                viewHolder1.tomatoSauceRadioBtn.setText(sauce.getTomatoSauce());
                viewHolder1.alfredoSauceRadioBtn.setText(sauce.getAlfredoSauce());

                // Clear the previously selected radio button
                viewHolder1.radioGroup.clearCheck();

                if (sauce.getSelectedSauce() != null) {
                    if (sauce.getSelectedSauce().equals(sauce.getTomatoSauce())) {
                        viewHolder1.radioGroup.check(viewHolder1.tomatoSauceRadioBtn.getId());
                    } else if (sauce.getSelectedSauce().equals(sauce.getAlfredoSauce())) {
                        viewHolder1.radioGroup.check((viewHolder1.tomatoSauceRadioBtn.getId()));
                    }
                }

                // Handling radio button changes
                viewHolder1.radioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
                    if (checkedId == viewHolder1.tomatoSauceRadioBtn.getId()) {
                        sauce.setSelected(sauce.getTomatoSauce());
                    } else if (checkedId == viewHolder1.alfredoSauceRadioBtn.getId()) {
                        sauce.setSelected(sauce.getAlfredoSauce());
                    } else {
                        sauce.setSelected(null);
                    }
                    if (toppingSelectionListener != null) {
                        toppingSelectionListener.onToppingSelectionChanged();
                    }
                }));
                break;

            case 1: // Meat selection
                int meatPosition = position - sauceList.size(); // Subtracting sauceList size from the position ensures correct index
                if (meatPosition >= 0 && meatPosition < meatList.size()) {
                    ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                    MeatDataModel meat = meatList.get(meatPosition);

                    viewHolder2.meatTextView.setText((meat.getMeat()));

                    viewHolder2.pepperoniCheckBox.setText((meat.getPepperoni()));
                    viewHolder2.pepperoniCheckBox.setChecked(meat.isPepperoniSelected());

                    viewHolder2.sausageCheckBox.setText((meat.getSausage()));
                    viewHolder2.sausageCheckBox.setChecked((meat.isSausageSelected()));

                    viewHolder2.baconCheckBox.setText(meat.getBacon());
                    viewHolder2.baconCheckBox.setChecked(meat.isBaconSelected());
                }
                break;

            case 2: // Veggie selection
                int veggiePosition = position - (sauceList.size() + meatList.size());
                if (veggiePosition >= 0 && veggiePosition < veggieList.size()) {
                    ViewHolder3 viewHolder3 = (ViewHolder3) holder;
                    VeggieDataModel veggie = veggieList.get(veggiePosition);

                    viewHolder3.veggiesTextView.setText(veggie.getVeggies());

                    viewHolder3.tomatoesCheckBox.setText(veggie.getTomatoes());
                    viewHolder3.tomatoesCheckBox.setChecked(veggie.isTomatoesSelected());

                    viewHolder3.peppersCheckBox.setText((veggie.getPeppers()));
                    viewHolder3.peppersCheckBox.setChecked(veggie.isPeppersSelected());

                    viewHolder3.mushroomCheckBox.setText(veggie.getMushrooms());
                    viewHolder3.mushroomCheckBox.setChecked(veggie.isMushroomsSelected());

                    viewHolder3.onionsCheckBox.setText(veggie.getOnions());
                    viewHolder3.onionsCheckBox.setChecked(veggie.isOnionsSelected());

                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sauceList.size() + meatList.size() + veggieList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView sauceTextView;
        RadioGroup radioGroup;
        RadioButton tomatoSauceRadioBtn, alfredoSauceRadioBtn;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            sauceTextView = itemView.findViewById(R.id.chooseSauce);
            tomatoSauceRadioBtn = itemView.findViewById(R.id.tomatoSauceBtn);
            alfredoSauceRadioBtn = itemView.findViewById(R.id.alfredoSauceBtn);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }

        void bindData(final SauceDataModel sauce) {
            sauceTextView.setText(sauce.getSauce());
            tomatoSauceRadioBtn.setText(sauce.getTomatoSauce());
            alfredoSauceRadioBtn.setText(sauce.getAlfredoSauce());

            // Clear selection
            radioGroup.clearCheck();

            if(sauce.getSelectedSauce() != null) {
                if (sauce.getSelectedSauce().equals(sauce.getTomatoSauce())) {
                    radioGroup.check(tomatoSauceRadioBtn.getId());
                } else if (sauce.getSelectedSauce().equals(sauce.getAlfredoSauce())) {
                    radioGroup.check(alfredoSauceRadioBtn.getId());
                }
            }

            radioGroup.setOnCheckedChangeListener(((group, checkedId) -> {
                if (checkedId == tomatoSauceRadioBtn.getId()) {
                    sauce.setSelected(sauce.getTomatoSauce());
                } else if (checkedId == alfredoSauceRadioBtn.getId()) {
                    sauce.setSelected(sauce.getAlfredoSauce());
                }
                if (toppingSelectionListener != null) {
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            }));
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView meatTextView;
        CheckBox pepperoniCheckBox, sausageCheckBox, baconCheckBox;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            meatTextView = itemView.findViewById(R.id.chooseMeat);
            pepperoniCheckBox = itemView.findViewById(R.id.pepperoniBox);
            sausageCheckBox = itemView.findViewById(R.id.sausageBox);
            baconCheckBox = itemView.findViewById(R.id.baconBox);

            setupCheckBoxListeners();
        }

        private void setupCheckBoxListeners() {

            pepperoniCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    MeatDataModel meat = meatList.get(getAdapterPosition() - sauceList.size());
                    meat.setPepperoniSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });

            sausageCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    MeatDataModel meat = meatList.get(getAdapterPosition() - sauceList.size());
                    meat.setSausageSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });

            baconCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    MeatDataModel meat = meatList.get(getAdapterPosition() - sauceList.size());
                    meat.setBaconSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });
        }

    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        TextView veggiesTextView;
        CheckBox tomatoesCheckBox, peppersCheckBox, mushroomCheckBox, onionsCheckBox;

        public ViewHolder3(@NonNull View itemView) {
            super(itemView);
            veggiesTextView = itemView.findViewById(R.id.chooseVeggies);
            tomatoesCheckBox = itemView.findViewById(R.id.tomatoBox);
            peppersCheckBox = itemView.findViewById(R.id.pepperBox);
            mushroomCheckBox = itemView.findViewById(R.id.mushroomBox);
            onionsCheckBox = itemView.findViewById(R.id.onionBox);

            setupCheckBoxListeners();
        }
        private void setupCheckBoxListeners() {
            tomatoesCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition() - sauceList.size() - meatList.size();
                if (position != RecyclerView.NO_POSITION) {
                    VeggieDataModel veggie = veggieList.get(position);
                    veggie.setTomatoesSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });
            peppersCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition() - sauceList.size() - meatList.size();
                if (position != RecyclerView.NO_POSITION) {
                    VeggieDataModel veggie = veggieList.get(position);
                    veggie.setPeppersSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });
            mushroomCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition() - sauceList.size() - meatList.size();
                if (position != RecyclerView.NO_POSITION) {
                    VeggieDataModel veggie = veggieList.get(position);
                    veggie.setMushroomsSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });
            onionsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition() - sauceList.size() - meatList.size();
                if (position != RecyclerView.NO_POSITION) {
                    VeggieDataModel veggie = veggieList.get(position);
                    veggie.setOnionsSelected(isChecked);
                    toppingSelectionListener.onToppingSelectionChanged();
                }
            });
        }
    }

    public interface ToppingSelectionListener {
        void onToppingSelectionChanged();
    }
}
