package main.java.com.ahh.school.service;

import main.java.com.ahh.school.dto.PayAmountDto;
import main.java.com.ahh.school.entity.PayAmount;
import main.java.com.ahh.school.repository.PayAmountRepository;

import java.util.ArrayList;
import java.util.List;

public class PayAmountServiceImpl implements PayAmountService{

    private PayAmountRepository repository;

    public PayAmountServiceImpl(PayAmountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PayAmountDto> fetchAll() {
        List<PayAmount> payAmounts = repository.fetchAll();
        List<PayAmountDto> payAmountDtos = new ArrayList<>();
        for (PayAmount out : payAmounts) {
            payAmountDtos.add(new PayAmountDto(out));
        }
        return payAmountDtos;
    }

    @Override
    public List<PayAmountDto> fetchAllByName(String name) {
        List<PayAmount> payAmounts = repository.fetchAllByName(name);
        List<PayAmountDto> payAmountDtos = new ArrayList<>();
        for (PayAmount out : payAmounts) {
            payAmountDtos.add(new PayAmountDto(out));
        }
        return payAmountDtos;
    }

    @Override
    public boolean save(PayAmountDto entity) {
        return repository.save(entity.getEntity());
    }

    @Override
    public boolean delete(PayAmountDto entity) {
        return repository.delete(entity.getEntity());
    }

    @Override
    public boolean deleteById(Long Id) {
        return repository.deleteById(Id);
    }

    @Override
    public PayAmountDto update(PayAmountDto entity) {
        PayAmount payAmount = repository.update(entity.getEntity());
        return new PayAmountDto(payAmount);
    }

    @Override
    public PayAmountDto findOne(Long aLong) {
        PayAmount payAmount = repository.findOne(aLong);
        return new PayAmountDto(payAmount);
    }
}
