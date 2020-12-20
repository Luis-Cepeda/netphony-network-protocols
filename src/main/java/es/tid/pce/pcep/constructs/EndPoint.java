package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.EndPointDataPathTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointUnnumberedDataPathTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.UnnumberedEndpointTLV;
import es.tid.pce.pcep.objects.tlvs.XifiEndPointTLV;

public class EndPoint extends PCEPConstruct {

	private EndPointIPv4TLV endPointIPv4;

	private EndPointDataPathTLV endPointDataPathID;
	
	private EndPointUnnumberedDataPathTLV endPointUnnumberedDataPathID;
	
	private UnnumberedEndpointTLV unnumberedEndpoint;

	private XifiEndPointTLV xifiEndPointTLV;

	public EndPoint() {

	}


	public EndPoint(byte[] bytes, int offset)throws MalformedPCEPObjectException {
		decode(bytes,offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		// TODO Auto-generated method stub
		int length=0;

		if (endPointIPv4!=null){
			endPointIPv4.encode();
			length=length+endPointIPv4.getTotalTLVLength();
		}
		else if (unnumberedEndpoint!=null){
			unnumberedEndpoint.encode();
			length=length+unnumberedEndpoint.getTotalTLVLength();
		}

		else if (xifiEndPointTLV != null)
		{
			xifiEndPointTLV.encode();
			length = length + xifiEndPointTLV.getTotalTLVLength();
		}
		else if (endPointDataPathID != null)
		{
			//log.info("EndPoint endPointDataPathID !=null ");
			endPointDataPathID.encode();
			//log.info("EndPoint endPointDataPathID tras encode:: "+endPointDataPathID.toString());
			length = length + endPointDataPathID.getTotalTLVLength();
		}
		else if (endPointUnnumberedDataPathID != null)
		{
			//log.info("EndPoint endPointDataPathID !=null ");
			endPointUnnumberedDataPathID.encode();
			//log.info("EndPoint endPointDataPathID tras encode:: "+endPointDataPathID.toString());
			length = length + endPointUnnumberedDataPathID.getTotalTLVLength();
		}


		this.setLength(length);
		this.bytes=new byte[this.getLength()];
		int offset=0;

		if (endPointIPv4!=null){
			System.arraycopy(endPointIPv4.getTlv_bytes(),0,this.bytes,offset,endPointIPv4.getTotalTLVLength());
			offset=offset+endPointIPv4.getTotalTLVLength();
		}

		else if (unnumberedEndpoint!=null){
			System.arraycopy(unnumberedEndpoint.getTlv_bytes(),0,this.bytes,offset,unnumberedEndpoint.getTotalTLVLength());
			offset=offset+unnumberedEndpoint.getTotalTLVLength();
		}

		else if (xifiEndPointTLV != null)
		{
			System.arraycopy(xifiEndPointTLV.getTlv_bytes(),0,this.bytes,offset,xifiEndPointTLV.getTotalTLVLength());
			offset = offset + xifiEndPointTLV.getTotalTLVLength();
		}
		else if (endPointDataPathID != null)
		{
			System.arraycopy(endPointDataPathID.getTlv_bytes(),0,this.bytes,offset,endPointDataPathID.getTotalTLVLength());
			offset = offset + endPointDataPathID.getTotalTLVLength();
		}
		else if (endPointUnnumberedDataPathID != null)
		{
			System.arraycopy(endPointUnnumberedDataPathID.getTlv_bytes(),0,this.bytes,offset,endPointUnnumberedDataPathID.getTotalTLVLength());
			offset = offset + endPointUnnumberedDataPathID.getTotalTLVLength();
		}

	}
	public void decode(byte[] bytes, int offset) throws MalformedPCEPObjectException {		
		int tlvtype=PCEPTLV.getType(bytes, offset);
		int tlvlength=PCEPTLV.getTotalTLVLength(bytes, offset);
		this.setLength(tlvlength);

		if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_IPV4_ADDRESS){
			endPointIPv4=new EndPointIPv4TLV(bytes, offset);
		}
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT){
			unnumberedEndpoint=new UnnumberedEndpointTLV(bytes, offset);
		}

		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_XIFI){
			xifiEndPointTLV = new XifiEndPointTLV(bytes, offset);
		}

		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_DATAPATHID){
			endPointDataPathID = new EndPointDataPathTLV(bytes, offset);
		}
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT_DATAPATHID){
			endPointUnnumberedDataPathID = new EndPointUnnumberedDataPathTLV(bytes, offset);
		}

	}


	public EndPointIPv4TLV getEndPointIPv4TLV() {
		return endPointIPv4;
	}

	public void setEndPointIPv4TLV(EndPointIPv4TLV EndPointIPv4TLV) {
		this.endPointIPv4 = EndPointIPv4TLV;
	}

	public XifiEndPointTLV getXifiEndPointTLV() 
	{
		return xifiEndPointTLV;
	}

	public void setXifiEndPointTLV(XifiEndPointTLV xifiEndPointTLV) 
	{
		this.xifiEndPointTLV = xifiEndPointTLV;
	}


	public UnnumberedEndpointTLV getUnnumberedEndpoint() {
		return unnumberedEndpoint;
	}


	public void setUnnumberedEndpoint(UnnumberedEndpointTLV unnumberedEndpoint) {
		this.unnumberedEndpoint = unnumberedEndpoint;
	}


	public EndPointDataPathTLV getEndPointDataPathTLV() {
		return endPointDataPathID;
	}


	public void setEndPointDataPathTLV(EndPointDataPathTLV endPointDataPathID) {
		this.endPointDataPathID = endPointDataPathID;
	}
	
	//Get and set of EndPointUnnumberedDataPathTLV
	public EndPointUnnumberedDataPathTLV getEndPointUnnumberedDataPathTLV() {
		return endPointUnnumberedDataPathID;
	}


	public void setEndPointUnnumberedDataPathTLV(EndPointUnnumberedDataPathTLV endPointUnnumberedDataPathID) {
		this.endPointUnnumberedDataPathID = endPointUnnumberedDataPathID;
	}


	public String toString(){
		if (endPointIPv4!=null){
			return endPointIPv4.getIPv4address().getHostAddress();
		}else if (endPointDataPathID != null ){
			return endPointDataPathID.getSwitchID();//.getDataPathID();

		}else if (endPointUnnumberedDataPathID != null ){
			return endPointUnnumberedDataPathID.getSwitchID()+"::"+endPointUnnumberedDataPathID.getIfID();//.getUnnumberedDataPathID();

		}else if (unnumberedEndpoint != null ){
			return unnumberedEndpoint.getIPv4address().getHostAddress()+":"+unnumberedEndpoint.getIfID();
		}else {
			return "";
		}
	}


	public EndPointIPv4TLV getEndPointIPv4() {
		return endPointIPv4;
	}


	public void setEndPointIPv4(EndPointIPv4TLV endPointIPv4) {
		this.endPointIPv4 = endPointIPv4;
	}


	public EndPointDataPathTLV getEndPointDataPathID() {
		return endPointDataPathID;
	}


	public void setEndPointDataPathID(EndPointDataPathTLV endPointDataPathID) {
		this.endPointDataPathID = endPointDataPathID;
	}


	public EndPointUnnumberedDataPathTLV getEndPointUnnumberedDataPathID() {
		return endPointUnnumberedDataPathID;
	}


	public void setEndPointUnnumberedDataPathID(
			EndPointUnnumberedDataPathTLV endPointUnnumberedDataPathID) {
		this.endPointUnnumberedDataPathID = endPointUnnumberedDataPathID;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((endPointDataPathID == null) ? 0 : endPointDataPathID
						.hashCode());
		result = prime * result
				+ ((endPointIPv4 == null) ? 0 : endPointIPv4.hashCode());
		result = prime
				* result
				+ ((endPointUnnumberedDataPathID == null) ? 0
						: endPointUnnumberedDataPathID.hashCode());
		result = prime
				* result
				+ ((unnumberedEndpoint == null) ? 0 : unnumberedEndpoint
						.hashCode());
		result = prime * result
				+ ((xifiEndPointTLV == null) ? 0 : xifiEndPointTLV.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EndPoint other = (EndPoint) obj;
		if (endPointDataPathID == null) {
			if (other.endPointDataPathID != null)
				return false;
		} else if (!endPointDataPathID.equals(other.endPointDataPathID))
			return false;
		if (endPointIPv4 == null) {
			if (other.endPointIPv4 != null)
				return false;
		} else if (!endPointIPv4.equals(other.endPointIPv4))
			return false;
		if (endPointUnnumberedDataPathID == null) {
			if (other.endPointUnnumberedDataPathID != null)
				return false;
		} else if (!endPointUnnumberedDataPathID
				.equals(other.endPointUnnumberedDataPathID))
			return false;
		if (unnumberedEndpoint == null) {
			if (other.unnumberedEndpoint != null)
				return false;
		} else if (!unnumberedEndpoint.equals(other.unnumberedEndpoint))
			return false;
		if (xifiEndPointTLV == null) {
			if (other.xifiEndPointTLV != null)
				return false;
		} else if (!xifiEndPointTLV.equals(other.xifiEndPointTLV))
			return false;
		return true;
	}
	
	


}