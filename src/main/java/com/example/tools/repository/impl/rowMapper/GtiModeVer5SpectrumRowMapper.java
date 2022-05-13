package com.example.tools.repository.impl.rowMapper;

import com.example.tools.model.GtiModeVer5Spectrum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GtiModeVer5SpectrumRowMapper implements RowMapper<GtiModeVer5Spectrum> {

    @Override
    public GtiModeVer5Spectrum mapRow(ResultSet rs, int i) throws SQLException {
        GtiModeVer5Spectrum obj = new GtiModeVer5Spectrum();
        obj.setMeterId(rs.getString("meter_id"));
        obj.setSpectrum(rs.getString("spectrum"));

        return obj;
    }
}
