package octanecde.digitalization.octanestrategylink.data.repository

import java.time.LocalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import octanecde.digitalization.octanestrategylink.data.model.ServiceModel

class ServiceRepository {
    private val services = listOf(
        service(
            1, "Digital Strategy Audit", "A rigorous review of your technology landscape, operating model, and investment priorities.",
            950.0, "Digital Strategy", 90, "photo-1451187580459-43490279c0fa",
            listOf("Technology maturity score", "Risk and opportunity map", "90-day action plan"),
        ),
        service(
            2, "Cybersecurity Assessment", "Identify critical exposure across infrastructure, identity, data, and operational processes.",
            1250.0, "Cybersecurity", 120, "photo-1563013544-824ae1b704d3",
            listOf("Threat surface review", "Control gap analysis", "Prioritised remediation plan"),
        ),
        service(
            3, "Cloud Readiness Review", "Build a pragmatic migration roadmap aligned with cost, resilience, and compliance needs.",
            1100.0, "Cloud Solutions", 90, "photo-1451187580459-43490279c0fa",
            listOf("Workload assessment", "Cloud cost model", "Migration wave plan"),
        ),
        service(
            4, "Process Automation Sprint", "Discover high-value automation opportunities and validate a focused delivery blueprint.",
            1450.0, "Process Optimisation", 120, "photo-1518770660439-4636190af475",
            listOf("Process mapping", "Automation shortlist", "Benefits forecast"),
        ),
        service(
            5, "Data & Analytics Roadmap", "Turn fragmented data into a governed foundation for reporting, insight, and AI.",
            1350.0, "Data & AI", 90, "photo-1551288049-bebda4e38f71",
            listOf("Data estate review", "Target architecture", "Analytics use-case backlog"),
        ),
        service(
            6, "IT Operating Model Design", "Clarify roles, governance, sourcing, and performance measures for a modern IT function.",
            1600.0, "Digital Strategy", 120, "photo-1521737711867-e3b97375f902",
            listOf("Capability map", "Governance design", "Service KPI framework"),
        ),
        service(
            7, "Technology Due Diligence", "Independent technical insight for investment, acquisition, and partnership decisions.",
            2200.0, "Risk & Assurance", 180, "photo-1556761175-b413da4baf72",
            listOf("Architecture review", "Delivery risk analysis", "Executive findings report"),
        ),
        service(
            8, "AI Opportunity Workshop", "Find responsible AI opportunities with clear value, feasibility, and adoption requirements.",
            800.0, "Data & AI", 90, "photo-1677442136019-21780ecad995",
            listOf("Use-case discovery", "Value-feasibility matrix", "Pilot recommendation"),
        ),
        service(
            9, "Business Continuity Review", "Strengthen technology resilience with tested recovery priorities and ownership.",
            1050.0, "Cybersecurity", 120, "photo-1516321318423-f06f85e504b3",
            listOf("Critical service mapping", "Recovery gap review", "Exercise plan"),
        ),
        service(
            10, "Vendor Selection Advisory", "Run a transparent evaluation of platforms and suppliers against real business needs.",
            1750.0, "Cloud Solutions", 120, "photo-1556761175-4b46a572b786",
            listOf("Requirements definition", "Weighted evaluation", "Negotiation support"),
        ),
        service(
            11, "Digital Transformation Coaching", "Give leadership teams the structure and confidence to steer complex change.",
            650.0, "Leadership", 60, "photo-1552664730-d307ca884978",
            listOf("Executive coaching", "Transformation scorecard", "Decision cadence"),
        ),
        service(
            12, "Architecture Health Check", "Assess scalability, maintainability, integration, and technical-debt hotspots.",
            1400.0, "Risk & Assurance", 120, "photo-1518770660439-4636190af475",
            listOf("Architecture review", "Technical debt heatmap", "Modernisation options"),
        ),
    )

    fun observeAll(): Flow<List<ServiceModel>> = flowOf(services)

    fun observeById(id: Int): Flow<ServiceModel?> = flowOf(getById(id))

    fun getById(id: Int): ServiceModel? = services.firstOrNull { it.id == id }

    private fun service(
        id: Int,
        name: String,
        description: String,
        price: Double,
        category: String,
        duration: Int,
        photoId: String,
        features: List<String>,
    ) = ServiceModel(
        id = id,
        name = name,
        description = description,
        price = price,
        availableTime = listOf(LocalTime.of(9, 30), LocalTime.of(11, 0), LocalTime.of(14, 30)),
        imageUrl = "https://images.unsplash.com/$photoId?auto=format&fit=crop&w=1200&q=85",
        category = category,
        durationMinutes = duration,
        features = features,
    )
}
