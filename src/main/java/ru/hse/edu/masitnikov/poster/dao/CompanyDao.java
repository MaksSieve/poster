package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Session;
import ru.hse.edu.masitnikov.poster.domain.Company;
import ru.hse.edu.masitnikov.poster.main.Main;

import java.util.List;

public class CompanyDao {

    private static final Session session = Main.getSession();

    @SuppressWarnings("unchecked")
    public List<Company> getList() {
        return session.createQuery("from Company ").list();
    }

    public void remove(Integer id) {
        session.beginTransaction();
        Company company = (Company) session.load(
                Company.class, id);
        if (null != company) {
            session.delete(company);
        }
        session.close();
    }

}
