package com.example.tugas1apap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.tugas1apap.dao.KecamatanMapper;
import com.example.tugas1apap.dao.KelurahanMapper;
import com.example.tugas1apap.dao.KotaMapper;
import com.example.tugas1apap.dao.PendudukMapper;

import lombok.extern.slf4j.Slf4j;

	@Slf4j
	@Service
	public class PendudukServiceDatabase implements PendudukService {
		
		@Autowired
		private PendudukMapper pendudukMapper;
		@Autowired
		private KotaMapper kotaMapper;
		@Autowired
		private KecamatanMapper kecamatanMapper;
		@Autowired
		private KelurahanMapper kelurahanMapper;
		
		public PendudukModel selectPenduduk(String nik) {
			log.info ("select citizen with nik {}", nik);
			return pendudukMapper.selectPenduduk(nik);

		}


		@Override
		public void updatePenduduk(PendudukModel penduduk) {
			 log.info ("update citizen info" );
			pendudukMapper.updatePenduduk(penduduk);
			
		}


		@Override
		public void pendudukIsWafat(String nik) {
			pendudukMapper.pendudukIsWafat(nik);
			
		}


		@Override
		public List<PendudukModel> getPendudukKelurahan(String id_kelurahan) {
			return pendudukMapper.getPendudukKelurahan(id_kelurahan);
		}


		@Override
		public List<PendudukModel> selectFamilyMember(String id_keluarga) {
			return pendudukMapper.selectFamilyMember(id_keluarga);
		}


		@Override
		public KeluargaModel getFamily(String id_keluarga) {
			return pendudukMapper.getFamily(id_keluarga);
		}

		@Override
		public List<KecamatanModel> getAllKecamatanInCity(String id_kota) {
			return kecamatanMapper.getAllKecamatanInCity(id_kota);
		}


		@Override
		public List<KelurahanModel> getAllKeluharanInKecamatan(String id_kecamatan) {
			return kelurahanMapper.getAllKelurahanInKecamatan(id_kecamatan);
		}


		@Override
		public KotaModel getCityById(String id_kota) {
			return kotaMapper.getCityById(id_kota);
		}
		
		public KecamatanModel getKecamatanById(String id_kecamatan) {
			return kecamatanMapper.getKecamatanById(id_kecamatan);
		}


		@Override
		public KelurahanModel getKelurahanById(String id_kelurahan) {
			return kelurahanMapper.getKelurahanById(id_kelurahan);
		}


		@Override
		public PendudukModel addPenduduk(PendudukModel penduduk) {
			
			String nik = generateNIK(penduduk);
			penduduk.setNik(nik);
			pendudukMapper.addPenduduk(penduduk);
			
			return penduduk;
			
		}


		@Override
		public String getKodeKecamatan(String id_keluarga) {
			return pendudukMapper.getKodeKecamatan(id_keluarga);
		}


		@Override
		public String generateNIK(PendudukModel penduduk) {
			//6 digit kode kecamatan
			 String kode_kecamatan = pendudukMapper.getKodeKecamatan(penduduk.getId_keluarga()).substring(0, 6);
			 
			 //6 digit tanggal lahir
			 String[] tanggal = penduduk.getTanggal_lahir().split("-");
			 log.info(tanggal + "tgl");
			 String tahun = tanggal[0].substring(2);
			 String bulan = tanggal[1];
			 String tgl = tanggal[2];
			 
			 //cek isPerempuan
			 if(penduduk.getJenis_kelamin() == 1) {
				 int date = Integer.parseInt(tgl);
				 date = date + 40;
				 tgl = Integer.toString(date);
			 }
			 
			 //4 digit terakhir
			 String prefix = kode_kecamatan + tgl + bulan + tahun;
			 String lastNIK = pendudukMapper.getLastNIK(prefix+"%");
			 
			 //generate new NIK
			 Long nik = Long.parseLong(prefix + "0001");
			 if(lastNIK != null) {
				 nik = Long.parseLong(lastNIK) + 1;
			 }
			 String nikBaru = Long.toString(nik);
			 penduduk.setNik(nikBaru);
			 
			 return nikBaru;
		}


		@Override
		public List<KotaModel> getAllKota() {
			return kotaMapper.getAllKota();
		}
}
