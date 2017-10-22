package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select id, kode_kota as kode_kota, nama_kota as nama_kota from kota;")
	public List <KotaModel> getAllKota ();
	
	@Select("select id, kode_kota as kode_kota, nama_kota as nama_kota from kota where id=#{id_kota}")
	public KotaModel getCityById (@Param("id_kota") String id_kota);
}
