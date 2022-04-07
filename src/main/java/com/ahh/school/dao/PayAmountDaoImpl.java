package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.entity.PayAmount;
import main.java.com.ahh.school.repository.PayAmountRepository;

import java.util.List;

public class PayAmountDaoImpl extends AbstractDaoImpl<PayAmount, Long> implements PayAmountRepository {
    @Override
    public boolean save(PayAmount entity) {
        return super.save(entity);
    }

    @Override
    public List<PayAmount> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public PayAmount update(PayAmount entity) {
        return super.update(entity);
    }

    @Override
    public boolean deleteById(Long aLong) {
        return super.deleteById(aLong);
    }

    @Override
    public List<PayAmount> fetchAllByName(String name) {
        return super.fetchAllByName(name);
    }
}
